package com.grupo4.restaurante.services;


import com.grupo4.restaurante.dto.UsuarioRegistroDTO;
import com.grupo4.restaurante.entities.Usuario;
import com.grupo4.restaurante.repositories.UsuarioRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service@AllArgsConstructor
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public boolean existeUsuarioPorUserName(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    public boolean existeUsuarioPorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    /*
     * Registra un nuevo usuario en el sistema.
     * Realiza validaciones de negocio y envía un correo de bienvenida.
     *
     * @param registroDTO DTO con los datos del usuario a registrar.
     * @return El objecto Usuario guardado.
     * @throws IllegalArgumentsException Si alguna validación de negocio falla (contraseñas no coinciden, rol no permitido, etc.).
     */
    public Usuario registrarNuevoUsuario(UsuarioRegistroDTO registroDTO) {

        // Validaciones de negocio (ya pasaron las de @Valid del DTO, pero estas son específicas del proceso de negocio).
        if (!registroDTO.getPassword().equals(registroDTO.getConfirmPassword())) {
            logger.warn("Intento de registro con contraseñas que no coinciden para el usuario: {}", registroDTO.getUsername());
            throw  new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        // Aunque el DTO ya tiene "CLIENTE" por defecto, es buena práctica validarlo aquí.
        // para evitar que alguien intente forzar otros roles si el DTO fuera manipulado.
        if (!"CLIENTE".equalsIgnoreCase(registroDTO.getRol())) {
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
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(registroDTO.getUsername());
        nuevoUsuario.setNombre(registroDTO.getNombre());
        nuevoUsuario.setPassword(passwordEncoder.encode(registroDTO.getPassword())); // Las contraseñas como los datos sensibles deben ir codificados nunca en bruto.
        nuevoUsuario.setEmail(registroDTO.getEmail());
        nuevoUsuario.setRol(registroDTO.getRol());  // El rol ya viene por defecto como "CLIENTE" desde el DTO.

        // Guardar el usuario en la BBDD.
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        logger.info("Usuario '{}' registrado con éxito.", usuarioGuardado.getUsername());

        // enviar correo de bienvenida (de forma asíncrona).
        if (usuarioGuardado.getEmail() != null && !usuarioGuardado.getEmail().isEmpty()) {
            String asunto = "¡Bienvenido a nuestro Restaurante, " + usuarioGuardado.getUsername() + "!";
            String cuerpoHTML = String.format(
                    """
                    <html><body>
                    <h1 style="color:#336699;">¡Hola, %s!</h1>
                    <p>¡Te damos la más cordial bienvenida a nuestro restaurante!</p>
                    <p>Estamos encantados de tenerte con nosotros. Explora nuestro <a href="http://localhost:8080/menu">manú</a> y haz tu primera reserva.</p>
                    <p>Si tienes alguna pregunta, no dudes en <a href="http://localhost:8080/contacto">contactarnos</a>.</p>
                    <p>¡Esperamos verte pronto!</p>
                    <br>
                    <Atentamente,</p>
                    <p>El equipo del Restaurante</p>
                    </body></html>
                    """, usuarioGuardado.getUsername()
            );
            // Los logs de éxito/fallo se manejan dentro de EmailService
            emailService.sendHtmlEmail(usuarioGuardado.getEmail(), asunto, cuerpoHTML);
        } else {
            logger.warn("No se pudo enviar el correo de bienvenida: el usuario '{}' no tiene una dirección de correo.", usuarioGuardado.getUsername());
        }

        return usuarioGuardado;
    }

    public boolean existeUsuarioPorUsername(@NotBlank(message = "El nombre de usuario es obligatorio.") String username) {
        return usuarioRepository.existsByUsername(username);
    }


}
