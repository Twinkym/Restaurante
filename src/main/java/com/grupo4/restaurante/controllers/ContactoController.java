package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.ContactoFormDTO;
import com.grupo4.restaurante.services.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador Spring MVC para la gestión del formulario de contacto.
 * Maneja la visualización del formulario y el procesamiento de los datos enviados.
 *
 * @author David De La Puente - KirgoDev - Grupo 4
 * @version 1.0
 * @since 2025-06-25
 */
@Controller
@RequestMapping("/contacto")  // URL base para este controlador.
@RequiredArgsConstructor
public class ContactoController {

    private final EmailService emailService; // Inyecta servicio de envío de correos.

    /*
     * Maneja peticiones GET a /contacto para mostrar el formulario de contacto.
     * @param modelo de Spring Model para pasar datos a la vista.
     * @return El nombre de la plantilla thymeleaf para el formulario de contacto.
     */
    @GetMapping
    public String mostrarFormularioContacto(Model model) {
        model.addAttribute("contactoForm", new ContactoFormDTO());
        model.addAttribute("tituloPagina", "Contacta con Nosotros");
        model.addAttribute("footerType", "footer-contacto");
        return "contacto";
    }

    /**
     * Procesa los datos enviados desde el formulario de contacto (POST).
     * Realiza validación de los datos y envía un correo electrónico.
     *
     * @param contactoFormDTO El DTO que contiene los datos del formulario (se valida automáticamente por @Valid).
     * @param bindingResult   Contiene los resultados de la validación.
     * @param redirectAttributes Utilizado para añadir mensajes flash (ej. éxito/error) para la redirección.
     * @param model Para añadir atributos al modelo si se vuelve a mostrar el formulario por errores de validación.
     * @return Una redirección a la página de contacto o la misma página si hay errores de validación.
     */
    @PostMapping
    public String procesarFormularioContacto(@Valid @ModelAttribute("contactoForm") ContactoFormDTO contactoFormDTO,
                                             BindingResult bindingResult,
                                             RedirectAttributes redirectAttributes,
                                             Model model) {
        // Si hay errores de validación (definidos en ContactoFormDTO),
        // volvemos a mostrar el formulario con los mensajes de error.
        if (bindingResult.hasErrors()) {
            model.addAttribute("tituloPagina", "Contacta con Nosotros"); // Mantiene el título de la página.
            // Los errores de validación se adjuntan automáticamente al modelo por BindingResult.
            return "contacto";  // Vuelve a la vista 'contacto.html' para mostrar los errores.
        }

        // Si la validación es exitosa, procede con el envío del correo.
        String emailSubjectForRestaurant = "Nuevo mensaje de contacto de " + contactoFormDTO.getNombre();
        String emailBodyForRestaurant = String.format(
                "Nombre: %s\n" +
                "Email: %s\n" +
                "Asunto: %s\n" +
                "Mensaje: \n%s\n\n" +
                "Acepta Privacidad: %b\n" +
                "Acepta Marketing: %b",
                contactoFormDTO.getNombre(),
                contactoFormDTO.getEmail(),
                contactoFormDTO.getAsunto(),
                contactoFormDTO.getMensaje(),
                contactoFormDTO.isAceptaPrivacidad(),
                contactoFormDTO.isAceptaMarketing()
        );

        // Intenta enviar el email al restaurante (puedes cambiar 'fromEmail' a un email fijo del restaurante)
        boolean restaurantEmailSent = emailService.sendSimpleEmail(
                emailService.getFromEmail(),
                emailSubjectForRestaurant,
                emailBodyForRestaurant
        );

        // Envía un correo de confirmación al usuario
        boolean confirmationEmailSent = emailService.sendContactConfirmationEmail(
                contactoFormDTO.getEmail(),
                contactoFormDTO.getNombre(),
                contactoFormDTO.getMensaje()
        );

        // Manejo de resultados y mensajes flash para el usuario.
        if (restaurantEmailSent) {
            redirectAttributes.addFlashAttribute("mensaje", "¡Gracias por tu mensaje! Lo hemos recibido y te responderemos pronto.");
            if (!confirmationEmailSent) {
                redirectAttributes.addFlashAttribute("error", "Tu mensaje ha sido enviado, pero no pudimos enviarte un correo de confirmación.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Lo sentimos, hubo un problema al enviar tu mensaje. Por favor, inténtalo de nuevo más tarde.");
        }

        // Patron PRG (Post/Redirect/Get) para evitar reenvió de formularios.
        return "redirect:/contacto";
    }
}
