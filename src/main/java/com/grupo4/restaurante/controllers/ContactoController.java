package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.ContactoFormDTO;
import com.grupo4.restaurante.services.EmailService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Data
@Controller
@RequestMapping("/contacto_form")  // URL base para este controlador.
@RequiredArgsConstructor
public class ContactoController {

    private final EmailService emailService; // Inyecta servicio de envío de correos.
    private static final Logger logger = LoggerFactory.getLogger(ContactoController.class); // Instancia del logger.

    /*
     * Maneja peticiones GET a /contacto para mostrar el formulario de contacto.
     * En los Mapeos de las funciones no necesitas indicar la ruta, ya que lo hacemos en la clase con RequestMapping("/contacto")
     * @param modelo de Spring Model para pasar datos a la vista.
     * @return El nombre de la plantilla thymeleaf para el formulario de contacto.
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioContacto(Model model) {
        model.addAttribute("contactoForm", new ContactoFormDTO());
        model.addAttribute("tituloPagina", "Formulario de Contacto");
        model.addAttribute("tituloCabecera", "Contacto");
        model.addAttribute("tituloContenido", "Envíanos tu mensaje");
        return "contacto_form";
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
                                             Model model,
                                             RedirectAttributes redirectAttributes) {
            // Sí hay errores de validación (definidos en ContactoFormDTO),
            // Validaciones del lado del servidor.
            // Volvemos a mostrar el formulario con los mensajes de error.
            if (bindingResult.hasErrors()) {
                model.addAttribute("contactoForm", contactoFormDTO);
                model.addAttribute("error", "Por favor, corrige los errores en el formulario.");

                model.addAttribute("tituloPagina", "Restaurante ERP - Contacto");
                model.addAttribute("tituloCabecera", "Contacta con Nosotros");
                model.addAttribute("tituloContenido", "Envíanos tu Mensaje.");
                model.addAttribute("footerType", "fragments-contacto :: footerContacto");
                return "contacto";  // Vuelve a la vista 'contacto_form.html' para mostrar los errores.
            }

            try {
                // Si la validación es exitosa, procede con el envío del correo.
                String emailSubjectForRestaurant = "Nuevo mensaje de contacto de " + contactoFormDTO.getNombre();
                String emailBodyForRestaurant = String.format(
                        """
                        Nombre: %s
                        Email: %s
                        Asunto: %s
                        Mensaje:
                        %s
                        
                        Acepta Privacidad: %b
                        Acepta Marketing: %b
                        """,
                        contactoFormDTO.getNombre(),
                        contactoFormDTO.getEmail(),
                        contactoFormDTO.getAsunto(),
                        contactoFormDTO.getMensaje(),
                        contactoFormDTO.getAceptaPrivacidad(),
                        contactoFormDTO.getAceptaMarketing()
                );

                String restauranteEmail = "contacto@restaurantegrupo4.com";

                boolean restaurantEmailSent = emailService.sendSimpleEmail(
                        restauranteEmail, // Destinatario.
                        emailSubjectForRestaurant,
                        emailBodyForRestaurant
                );

                // Envía un correo de confirmación al usuario
                boolean confirmationEmailSent = false;
                if (restaurantEmailSent) {
                    confirmationEmailSent = emailService.sendContactConfirmationEmail(
                            contactoFormDTO.getEmail(),
                            contactoFormDTO.getNombre(),
                            contactoFormDTO.getMensaje()
                    );
                }

                // Manejo de resultados y mensajes flash para el usuario.
                if (restaurantEmailSent && confirmationEmailSent) {
                    redirectAttributes.addFlashAttribute("mensaje", "¡Gracias por tu mensaje! Lo hemos recibido y te responderemos pronto.");
                } else if (restaurantEmailSent){
                    redirectAttributes.addFlashAttribute("mensaje", "¡Tu mensaje ha sido enviado! Pero tuvimos un problema al enviarte la confirmación por correo.");
                    redirectAttributes.addFlashAttribute("error", "Por favor, revisa tu carpeta de spam o inténtalo de nuevo más tarde si no recibes la confirmación.");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Lo sentimos, hubo un problema al enviar tu mensaje. Por favor, inténtalo de nuevo más tarde.");
                }
            } catch (Exception e) {
                   //System.out.println("Error al enviar al correo de contacto: " + e.getMessage());
                    logger.error("Error al enviar el correo de contacto: {}", e.getMessage(), e);  // Usamos {} el mensaje y 'e' para el stack tracé.
                    redirectAttributes.addFlashAttribute("error", "Hubo un error al procesar tu mensaje. intentalo de nuevo.");
                }
                // Patron PRG (Post/Redirect/Get) para evitar reenvió de formularios.
                return "redirect:/contacto";
    }
}
