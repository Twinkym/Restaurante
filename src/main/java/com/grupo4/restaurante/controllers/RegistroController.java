package com.grupo4.restaurante.controllers;

/*
 * Mostrar el formulario en /registro(GET).
 * Procesar el envío (POST).
 * Validar el formulario con @Valid.
 * Guardar el mensaje como entidad Contacto.
 * Redirigir a una vista de confirmación o a la misma con errores.
 *
 * @author David De La Puente - KirgoDev
 * @version 1.1
 * @since 2025-09-06
 *
 */

import com.grupo4.restaurante.dto.UsuarioRegistroDTO;
import com.grupo4.restaurante.entities.Usuario;
import com.grupo4.restaurante.services.TitleService;
import com.grupo4.restaurante.services.UsuarioService;
import com.grupo4.restaurante.services.VerificationTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RegistroController {

    private final UsuarioService usuarioService;
    private final TitleService titleService;
    private final VerificationTokenService verificationTokenService;

    @GetMapping({"/auth/registro", "/registro"})
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioRegistroDTO());
        model.addAttribute("tituloPagina", titleService.getTituloPagina("registro"));
        model.addAttribute("tituloCabecera", titleService.getTituloCabecera("registro"));
        model.addAttribute("tituloContenido", "Formulario de Registro");
        return "auth/registro";
    }

    /**
     * Endpoint que procesa el enlace de confirmación recibido por e-mail.
     * Recomendación: usar RedirectAttributes para flash messages y no tocar repositorios desde el controlador.
     */
    @GetMapping("/auth/confirm")
    public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        Optional<Usuario> optUser = verificationTokenService.validateVerificationToken(token);

        if (optUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorConfirm", "Token inválido o expirado.");
            return "redirect:/auth/login";
        }

        Usuario user = optUser.get();

        // Delegar siempre en el service la activación y persistencia del usuario.
        usuarioService.activarUsuario(user);

        redirectAttributes.addFlashAttribute("confirmado", "Cuenta confirmada. Ya puedes iniciar sesión.");
        return "redirect:/login";
    }
    @PostMapping({"/auth/registro", "/registro"})
    public String guardarUsuario(
            @ModelAttribute("usuario") @Valid UsuarioRegistroDTO usuarioRegistroDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("tituloPagina", titleService.getTituloPagina("registro"));
            model.addAttribute("tituloCabecera", titleService.getTituloCabecera("registro"));
            model.addAttribute("tituloContenido", "Formulario de Registro");
            return "auth/registro";
        }

        try {
            // Validar el nombre de usuario existente antes de llamar al servicio.
            // Aunque el servicio también lo valida, es bueno tener un chack rápido aquí para la UI.
            if (usuarioService.existeUsuarioPorUsername(usuarioRegistroDTO.getUsername())) {
                model.addAttribute("error", "Ya existe un usuario con ese nombre de usuario.");
                model.addAttribute("tituloPagina", titleService.getTituloPagina("registro"));
                model.addAttribute("tituloCabecera", titleService.getTituloCabecera("registro"));
                model.addAttribute("tituloContenido", "Formulario de Registro");
                return "auth/registro";
            }
            // Aquí el servicio maneja todas las validaciones restantes, la creación del usuario,
            // la codificación de la contraseña, el guardado y el envío del email.
            usuarioService.registrarNuevoUsuario(usuarioRegistroDTO);

            redirectAttributes.addFlashAttribute("registroExitoso", "¡Registro exitoso! Ya puedes iniciar sesión.");
            return "redirect:/login";

        } catch (IllegalArgumentException e) {
            // Captura excepciones de negocio lanzadas por UsuarioService
            model.addAttribute("error", e.getMessage());
            model.addAttribute("tituloPagina", titleService.getTituloPagina("registro"));
            model.addAttribute("tituloCabecera", titleService.getTituloCabecera("registro"));
            model.addAttribute("tituloContenido", "Formulario de Registro");
            return "auth/registro"; // Vuelve al formulario con el mensaje de error.
        } catch (Exception e) {
            // Captura cualquier otro error inesperado (ej. problemas de BBDD no manejadas específicamente).
            model.addAttribute("error", "Ocurrió un error inesperado durante el registro. Por favor, inténtalo de nuevo más tarde.");
            model.addAttribute("tituloPagina", titleService.getTituloPagina("registro"));
            model.addAttribute("tituloCabecera", titleService.getTituloCabecera("registro"));
            model.addAttribute("tituloContenido", "Formulario de Registro");
            return "auth/registro";
        }
    }


}
