package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.ReservaFormDTO;
import com.grupo4.restaurante.dto.TurnoInfoDTO;
import com.grupo4.restaurante.entities.Reserva;
import com.grupo4.restaurante.entities.Restaurante;
import com.grupo4.restaurante.services.MesaService;
import com.grupo4.restaurante.services.ReservaService;
import com.grupo4.restaurante.services.TurnoService;
import com.grupo4.restaurante.validaciones.ValidadorERP;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Data
@Controller
@RequestMapping("/reservas") // Base path for all methods in this controller is /reservas
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;
    private final TurnoService turnoService;
    private final MesaService mesaService;

    // Helper method to load common model attributes for the reservation form
    private void cargarDatosModelo(Model model, ReservaFormDTO reservaForm, List<TurnoInfoDTO> turnos, Boolean mostrarTurnos, String tituloPagina, String tituloCabecera, String tituloContenido) {
        model.addAttribute("reservaForm", reservaForm);
        model.addAttribute("capacidades", IntStream.rangeClosed(1, 8).boxed().toList());
        model.addAttribute("turnosDisponibles", mostrarTurnos ? turnoService.obtenerTurnosDisponibles(reservaForm.getFecha(), reservaForm.getNumComensales()) : new ArrayList<>());
        // Title for the form page, used when creating or editing
        model.addAttribute("tituloPagina", tituloPagina);
        model.addAttribute("tituloCabecera", tituloCabecera);
        model.addAttribute("tituloContenido", tituloContenido);
    }

    // -- LISTAR TODAS LAS RESERVAS (READ ALL) --
    // Maps to GET /reservas
    @GetMapping // Este meto-do maneja la ruta raíz , i.e., /reservas
    public String listReservas(Model model) {
        List<Reserva> reservas = reservaService.findAll(); // LLama al servicio para obtener las reservas.
        model.addAttribute("reservas", reservas); // Añade la lista para el modelo thymeleaf.

        // Títulos específicos para la página de listado de reservas
        model.addAttribute("tituloPagina", "Restaurante ERP - Reservas");
        model.addAttribute("tituloCabecera", "Gestión de Reservas");
        model.addAttribute("tituloContenido", "Listado de Reservas Confirmadas"); // Fija el título para el h2 de la tabla.

        return "reservas/reserva_list"; // Return the list Thymeleaf template
    }

    // -- MOSTRAR FORMULARIO PARA CREAR NUEVA RESERVA (CREATE) --
    // Maps to GET /reservas/nueva
    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        ReservaFormDTO reservaForm = new ReservaFormDTO();
        reservaForm.setFecha(LocalDate.now());
        reservaForm.setNumComensales(1); // Set a default value

        // Llamada al helper con los títulos deseados para "nueva Reserva".
        cargarDatosModelo(model, reservaForm, new ArrayList<>(), false,
                "Restaurante ERP - Reservas",
                "Crear Nueva Reserva",
                "Formulario de Nueva Reserva");
        return "reservas/reserva_form";
    }

    // -- FILTRAR TURNOS DISPONIBLES EN EL FORMULARIO --
    // Maps to POST /reservas/filtrar-turnos
    @PostMapping("/filtrar-turnos")
    public String filtrarTurnos(@ModelAttribute ReservaFormDTO reservaFormDTO, Model model) {
        ValidadorERP.normalizarReservaForm(reservaFormDTO);
        List<TurnoInfoDTO> turnos = turnoService.obtenerTurnosDisponibles(reservaFormDTO.getFecha(), reservaFormDTO.getNumComensales());
        cargarDatosModelo(model, reservaFormDTO, turnos, true,
                "Restaurante ERP - Crear/Editar ReservaS",
                "Gestión de Reservas",
                "Formulario de Reserva");
        return "reservas/reserva_form"; // Always return to the form after filtering
    }

    // -- PROCESAR FORMULARIO DE GUARDADO (CREATE) --
    // Maps to POST /reservas/guardar
    @PostMapping("/guardar")
    public String procesarFormulario(@ModelAttribute ReservaFormDTO reservaFormDTO, Model model, RedirectAttributes redirectAttributes) {
        ValidadorERP.normalizarReservaForm(reservaFormDTO);

        if (!ValidadorERP.validarTurnoSeleccionado(reservaFormDTO)) {
            cargarDatosModelo(model, reservaFormDTO, turnoService.obtenerTurnosDisponibles(reservaFormDTO.getFecha(), reservaFormDTO.getNumComensales()), true,
                    "Restaurante ERP - Crear Nueva Reserva",
                    "Crear Nueva Reserva",
                    "Formulario de Nueva Reserva");
            model.addAttribute("mensaje", "Por favor, seleccione un turno.");
            return "reservas/reserva_form";
        }

        Optional<Reserva> reserva = reservaService.crearReserva(reservaFormDTO);
        if (reserva.isPresent()) {
            redirectAttributes.addFlashAttribute("mensaje", "Su reserva para " + reserva.get().getNombreCliente() + " ha sido procesada con éxito!");
            return "redirect:/reservas"; // Redirect to the list view after success
        } else {
            // Recargar Datos y títulos si no hay mesas disponibles.
            cargarDatosModelo(model, reservaFormDTO, turnoService.obtenerTurnosDisponibles(reservaFormDTO.getFecha(), reservaFormDTO.getNumComensales()), true,
                    "Restaurante ERP - Crear Nueva Reserva",
                    "Crear Nueva Reserva",
                    "Formulario de Nueva Reserva");
            model.addAttribute("mensaje", "No hay mesas disponibles para esa fecha y hora. Intente con otra combinación.");
            return "reservas/reserva_form"; // Return to form with an error message
        }
    }

    // -- VER DETALLES DE UNA RESERVA (READ ONE) --
    // Maps to GET /reservas/{id}
    @GetMapping("/{id}")
    public String verDetalleReserva(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Reserva> reservaOptional = reservaService.findById(id);
        if (reservaOptional.isPresent()) {
            model.addAttribute("reserva", reservaOptional.get());
            model.addAttribute("tituloPagina", "Detalles de la Reserva #" + id);
            model.addAttribute("tituloCabecera", "Detalles de la Reserva");
            model.addAttribute("tituloContenido", "Detalles de la reserva #" + id);
            return "reservas/reserva_detail"; // Template for reservation details
        } else {
            redirectAttributes.addFlashAttribute("error", "Reserva no encontrada.");
            return "redirect:/reservas"; // Redirect back to list if not found
        }
    }

    // -- MOSTRAR FORMULARIO PARA EDITAR RESERVA (UPDATE) --
    // Maps to GET /reservas/editar/{id}
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Reserva> reservaOptional = reservaService.findById(id);
        if (reservaOptional.isPresent()) {
            Reserva reserva = reservaOptional.get();
            // Convert Reserva entity to ReservaFormDTO for pre-populating the form
            ReservaFormDTO reservaForm = ReservaFormDTO.builder()
                    .id(reserva.getId())
                    .nombreCliente(reserva.getNombreCliente())
                    .emailCliente(reserva.getEmail()) // Asumiendo que 'email' en Reserva es 'emailCliente' en DTO.
                    .telefono(reserva.getTelefono())
                    .numComensales(reserva.getNumComensales())
                    .fecha(reserva.getFecha())
                    .horaTurno(reserva.getHora()) // Asumiendo que 'hora' en Reserva es 'horaTurno' en DTO.
                    .build();

            List<TurnoInfoDTO> turnos = turnoService.obtenerTurnosDisponibles(reserva.getFecha(), reserva.getNumComensales());

            cargarDatosModelo(model, reservaForm, turnos, true,
                    "Restaurante ERP - Editar Reserva #" + id,
                    "Editar Reserva",
                    "Formulario de Edición de Reserva #" + id);
            return "reservas/reserva_form"; // Reusar el form existente para editar
        } else {
            redirectAttributes.addFlashAttribute("error", "Reserva a editar no encontrada.");
            return "redirect:/reservas";
        }
    }

    // -- PROCESAR EDICIÓN DE RESERVA (UPDATE) --
    // Maps to POST /reservas/editar/{id}
    @PostMapping("/editar/{id}")
    public String procesarEdicion(@PathVariable Long id, @ModelAttribute ReservaFormDTO reservaFormDTO, Model model, RedirectAttributes redirectAttributes) {
        reservaFormDTO.setId(id); // Ensure the ID from the path is set in the DTO

        ValidadorERP.normalizarReservaForm(reservaFormDTO);

        if (!ValidadorERP.validarTurnoSeleccionado(reservaFormDTO)) {
            cargarDatosModelo(model, reservaFormDTO, turnoService.obtenerTurnosDisponibles(reservaFormDTO.getFecha(), reservaFormDTO.getNumComensales()), true,
                    "Restaurante ERP - Editar Reserva #" +id,
                    "Editar Reserva",
                    "Formulario de Edición de Reserva #" + id);
            model.addAttribute("mensaje", "Por favor, seleccione un turno.");
            model.addAttribute("tituloPagina", "Editar Reserva #" + id);
            return "reservas/reserva_form";
        }

        Optional<Reserva> updatedReserva = reservaService.updateReserva(reservaFormDTO);
        if (updatedReserva.isPresent()) {
            redirectAttributes.addFlashAttribute("mensaje", "Reserva #" + id + " actualizada con éxito!");
            return "redirect:/reservas";
        } else {
            cargarDatosModelo(model, reservaFormDTO, turnoService.obtenerTurnosDisponibles(reservaFormDTO.getFecha(), reservaFormDTO.getNumComensales()), true,
                    "Restaurante ERP - Editar Reserva #" + id,
                    "Editar Reserva",
                    "Formulario de Edición de Reserva #" + id);
            model.addAttribute("mensaje", "No se pudo actualizar la reserva. No hay mesas disponibles para la nueva fecha y hora.");
            return "reservas/reserva_form";
        }
    }

    // -- ELIMINAR RESERVA (DELETE) --
    // Maps to POST /reservas/{id}/delete
    @PostMapping("/{id}/delete")
    public String eliminarReserva(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservaService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva #" + id + " eliminada con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la reserva #" + id + ": " + e.getMessage());
        }
        return "redirect:/reservas";
    }

    // -- PÁGINA DE CONFIRMACIÓN (AFTER CREATION) --
    // Maps to GET /reservas/confirmación
    @GetMapping("/confirmacion")
    public String confirmacionReserva(Model model) {
        model.addAttribute("mensaje", "Su Reserva ha sido procesada con Éxito!!!");
        model.addAttribute("tituloPagina", "Restaurante ERP - Confirmación de Reserva");
        model.addAttribute("tituloCabecera", "Reserva Confirmada.");
        model.addAttribute("tituloContenido", "¡Gracias por su Reserva!");
        return "reservas/confirmacion_reserva";
    }
}