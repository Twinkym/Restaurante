package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.UsuarioRegistroDTO;
import com.grupo4.restaurante.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
//@RequestMapping("/auth")
@AllArgsConstructor
public class RegistroController {

    private final UsuarioService usuarioService;

    @GetMapping("/auth/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioRegistroDTO());
        return "auth/registro";
    }

    @PostMapping("/auth/registro")
    public String guardarUsuario(@ModelAttribute("usuario") @Valid UsuarioRegistroDTO usuarioRegistroDTO, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "auth/registro";
        }

        try {
            // Validar el nombre de usuario existente antes de llamar al servicio.
            // Aunque el servicio también lo valida, es bueno tener un chack rápido aquí para la UI.
            if (usuarioService.existeUsuarioPorUsername(usuarioRegistroDTO.getUsername())) {
                model.addAttribute("error", "Ya existe un usuario con ese nombre de usuario.");
                return "auth/registro";
            }

            // Aquí el servicio maneja todas las validaciones restantes, la creación del usuario,
            // la codificación de la contraseña, el guardado y el envío del email.
            usuarioService.registrarNuevoUsuario(usuarioRegistroDTO);

            redirectAttributes.addFlashAttribute("registroExitoso", "¡Registro exitoso! Ya puedes iniciar sesión.");
            return "redirect:auth/login";

        } catch (IllegalArgumentException e) {
            // Captura excepciones de negocio lanzadas por UsuarioService
            model.addAttribute("error", e.getMessage());
            return "auth/registro"; // Vuelve al formulario con el mensaje de error.
        } catch (Exception e) {
            // Captura cualquier otro error inesperado (ej. problemas de BBDD no manejadas específicamente).
            model.addAttribute("error", "Ocurrió un error inesperado durante el registro. Por favor, inténtalo de nuevo más tarde.");
            return "auth/registro";
        }
    }
}
