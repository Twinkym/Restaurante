package com.grupo4.restaurante.controllers;

/*
 * Mostrar el formulario en /contacto(GET).
 * Procesar el envío (POST).
 * Validar el formulario con @Valid.
 * Guardar el mensaje como entidad Contacto.
 * Redirigir a una vista de confirmación o a la misma con errores.
 *
 * @author David De La Puente - KirgoDev
 * @version 1.1
 * @since 2025-07-02
 *
 */

import com.grupo4.restaurante.dto.ContactoDTO;
import com.grupo4.restaurante.entities.Contacto;
import com.grupo4.restaurante.repositories.ContactoRepository;
import com.grupo4.restaurante.services.TitleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contacto")
public class ContactoController {

    private final TitleService titleService;
    private final ContactoRepository contactoRepository;

    @GetMapping("/nuevo")
    public String mostrarFormularioContacto(Model model) {
        model.addAttribute("contactoDTO", new ContactoDTO());
        model.addAttribute("tituloPagina", titleService.getTituloPagina("contacto"));
        model.addAttribute("tituloCabecera", titleService.getTituloCabecera("contacto"));
        model.addAttribute("tituloContenido", "Formulario de Contacto");

        return "contacto/contacto";
    }
    /*
     * Muestra el formulario de contacto.
     *
     * @param model el modelo de datos para la vista.
     * @return la vista del formulario de contacto.
     */
    @GetMapping("/contacto")
    public String mostrarFormulario(Model model) {
        model.addAttribute("contactoDTO", new ContactoDTO());
        model.addAttribute("tituloPagina", "Formulario de contacto");
        model.addAttribute("tituloCabecera", "Contáctanos");
        model.addAttribute("tituloContenido", "Envianos tu mensaje.");
        return "contacto/contacto";
    }

    /*
     * Procesa el formulario de contacto.
     *
     * @param contactoDTO datos del formulario.
     * @param bindingResult resultados de la validación.
     * @para model modelo para la vista.
     * @return redirección o vista con errores.
     */
    @PostMapping("/contacto")
    public String procesarFormulario(
            @Valid @ModelAttribute("contactoDTO") ContactoDTO contactoDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "contacto";  // vuelve al formulario con errores.
        }

        // Crear entidad Contacto a partir del DTO
        Contacto contacto = Contacto.builder()
                .nombre(contactoDTO.getNombre())
                .email(contactoDTO.getEmail())
                .asunto(contactoDTO.getAsunto())
                .mensaje(contactoDTO.getMensaje())
                .fecha(LocalDateTime.now())
                .build();

        contactoRepository.save(contacto);

        return "redirect:/contacto-confirmación";
    }
}