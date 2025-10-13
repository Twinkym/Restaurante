package com.grupo4.restaurante.services;

/*
*
* */

import com.grupo4.restaurante.dto.UsuarioRegistroDTO;
import com.grupo4.restaurante.entities.Rol;
import com.grupo4.restaurante.entities.Usuario;
import com.grupo4.restaurante.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;

    public boolean existeUsuarioPorUsername(@NotBlank(message = "El nombre de usuario es obligatorio.") String username) {
        return usuarioRepository.existsByUsername(username);
    }

    public boolean existeUsuarioPorEmail(@NotBlank(message = "El email es obligatorio.") String email) {
        return usuarioRepository.existsByUsername(email);
    }
    /*
     * Registra un nuevo usuario en el sistema.
     * Realiza validaciones de negocio y envía un correo de bienvenida.
     *
     * @param registroDTO DTO con los datos del usuario a registrar.
     * @return El objecto Usuario guardado.
     * @throws IllegalArgumentsException Si alguna validación de negocio falla (contraseñas no coinciden, rol no permitido, etc.).
     */
    @Transactional
    public Usuario registrarNuevoUsuario(UsuarioRegistroDTO registroDTO) {

        // Normalizar input (Asumimos NPE: asumimos @Valid en el controlador)
        String username = registroDTO.getUsername().trim();
        String email = registroDTO.getEmail().trim().toLowerCase();

        // Validaciones de negocio (ya pasaron las de @Valid del DTO, pero estas son específicas del proceso de negocio).
        if (!registroDTO.getPassword().equals(registroDTO.getConfirmPassword())) {
            logger.warn("Intento de registro con contraseñas que no coinciden para el usuario: {}", username);
            throw  new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        if (existeUsuarioPorUsername(username)) {
            logger.warn("El nombre de usuario ya existe en el sistema: {}", username);
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre de usuario.");
        }

        if (existeUsuarioPorEmail(email)) {
            logger.warn("El email de usuario ya existe en el sistema: {}", email);
            throw new IllegalArgumentException("Ya existe un usuario con ese email.");
        }
        // Aunque el DTO ya tiene "CLIENTE" por defecto, es buena práctica validarlo aquí.
        // para evitar que alguien intente forzar otros roles si el DTO fuera manipulado.
        if ((registroDTO.getRol() != Rol.CLIENTE)) {
            // Para registro público, solo se permite CLIENTE.
            logger.warn("Intento de registro con rol no permitido: {}", registroDTO.getRol());
            throw new IllegalArgumentException("No puedes asignarte un rol diferente a CLIENTE en el registro público.");
        }

        // validar si el email ya existe (además del username).
        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            logger.warn("Intento de registro con email ya existente: {}", registroDTO.getEmail());
            throw new IllegalArgumentException("Ya existe un usuario con ese email.");
        }

        // Crear el objeto Usuario desde el DTO.
        Usuario nuevoUsuario =  Usuario.builder()
                .username(username)
                .nombre(registroDTO.getNombre())
                .password(passwordEncoder.encode(registroDTO.getPassword())) // Las contraseñas como los datos sensibles deben ir codificados nunca en bruto.
                .email(email)
                .rol(Rol.CLIENTE)
                .enabled(false)
                .build();// El rol ya viene por defecto como "CLIENTE" desde el DTO.

        // Guardar el usuario en la BBDD.
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        logger.info("Usuario '{}' registrado con éxito (pendiente verificación de e-mail).", usuarioGuardado.getUsername());

        // Crear token de verificación y guardarlo.
        String token = UUID.randomUUID().toString();
        verificationTokenService.createTokenForUser(usuarioGuardado, token);

        // Envío de correo ASÍNCRONO con link de confirmación
        String asunto = "Confirma tu cuenta en Restaurante";
        String link = "http://localhost:8080/auth/confirm?token=" + token;
        String cuerpoHTML = "<p>Hola " + usuarioGuardado.getUsername() + "</p>"
                + "<p>Gracias por registrarte. Confirma tu correo haciendo click <a href=\"" + link + "\">aqui</a>.</p>"
                + "<p>Si no solicitaste esto, ignora este correo.</p>";

        // Envío asíncrono (no bloqueante)
        try {
            emailService.sendHtmlEmailAsync(usuarioGuardado.getEmail(), asunto, cuerpoHTML);
        } catch (Exception e) {
            // No hacemos rollback por fallo de email; el usuario puede reintentar reenvío.
            logger.error("Error disparando el envío de verificación para '{}'", usuarioGuardado.getUsername(), e);
        }

        /* enviar correo de bienvenida (de forma asíncrona).
         * if (usuarioGuardado.getEmail() != null && !usuarioGuardado.getEmail().isEmpty()) {
         *  String asunto = "¡Bienvenido a nuestro Restaurante, " + usuarioGuardado.getUsername() + "!";
         *   String cuerpoHTML = String.format(
         *           """
         *           <html><body>
         *           <h1 style="color:#336699;">¡Hola, %s!</h1>
         *           <p>¡Te damos la más cordial bienvenida a nuestro restaurante!</p>
         *           <p>Estamos encantados de tenerte con nosotros. Explora nuestro <a href="http://localhost:8080/menu">manú</a> y haz tu primera reserva.</p>
         *           <p>Si tienes alguna pregunta, no dudes en <a href="http://localhost:8080/contacto">contactarnos</a>.</p>
         *           <p>¡Esperamos verte pronto!</p>
         *           <br>
         *           <Atentamente,</p>
         *           <p>El equipo del Restaurante</p>
         *           </body></html>
         *           """, usuarioGuardado.getUsername()
         *   );
         *   // Los logs de éxito/fallo se manejan dentro de EmailService
         *   emailService.sendHtmlEmail(usuarioGuardado.getEmail(), asunto, cuerpoHTML);
         * } else {
         *   logger.warn("No se pudo enviar el correo de bienvenida: el usuario '{}' no tiene una dirección de correo.", usuarioGuardado.getUsername());
         * }
         */

        return usuarioGuardado;
    }

    /*
     * Activa un usuario (se usa en el controlador al confirmar token).
     * Lo dejamos transaccional por seguridad.
     */
    @Transactional
    public void activarUsuario(Usuario user) {
        if (user == null) return;
        user.setEnabled(true);
        usuarioRepository.save(user);
        logger.info("Usuario '{}' activado ya puede loguearse con su cuenta.", user.getUsername());
    }
}
