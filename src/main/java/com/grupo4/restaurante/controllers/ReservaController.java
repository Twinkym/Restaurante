package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.ReservaForm;
import com.grupo4.restaurante.dto.TurnoInfoDTO;
import com.grupo4.restaurante.entities.Reserva;
import com.grupo4.restaurante.services.MesaService;
import com.grupo4.restaurante.services.ReservaService;
import com.grupo4.restaurante.services.TurnoService;
import com.grupo4.restaurante.validaciones.ValidadorERP;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Data
@Controller
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;
    private final TurnoService turnoService;
    private final MesaService mesaService;

    private void cargarDatosModelo(Model model, ReservaForm reservaForm, List<TurnoInfoDTO> turnos, Boolean mostrarTurnos) {
        model.addAttribute("reservaForm", reservaForm);
        model.addAttribute("capacidades", IntStream.rangeClosed(1, 8).boxed().toList());
        model.addAttribute("turnosDisponibles", mostrarTurnos ? turnoService.obtenerTurnosDisponibles(reservaForm.getFecha(), reservaForm.getNumComensales()) : new ArrayList<>());
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        ReservaForm reservaForm = new ReservaForm();
        reservaForm.setFecha(LocalDate.now());
        reservaForm.setNumComensales(1); // Añado valor por defecto.
        List<TurnoInfoDTO> turnos = turnoService.obtenerTurnosDisponibles(reservaForm.getFecha(), reservaForm.getNumComensales());
        cargarDatosModelo(model, reservaForm, turnos, false);
        return "reservas/reserva_form";
    }

    @PostMapping("/filtrar-turnos")
    public String filtrarTurnos(@ModelAttribute ReservaForm reservaForm, Model model) {
        // Validaciones básicas y normalización.
        ValidadorERP.normalizarReservaForm(reservaForm);

        List<TurnoInfoDTO> turnos = turnoService.obtenerTurnosDisponibles(reservaForm.getFecha(), reservaForm.getNumComensales());

        cargarDatosModelo(model, reservaForm, turnos, true);

        return "reservas/reserva_form";
    }
    @PostMapping("/guardar")
    public String procesarFormulario(@ModelAttribute ReservaForm reservaForm, Model model) {
        // Normalizamos campos antes de validar.
        ValidadorERP.normalizarReservaForm(reservaForm);
        // Validación del turno seleccionado.
        if (!ValidadorERP.validarTurnoSeleccionado(reservaForm)) {
            cargarDatosModelo(model, reservaForm, turnoService.obtenerTurnosDisponibles(reservaForm.getFecha(), reservaForm.getNumComensales()), true);
            model.addAttribute("mensaje", "Por favor, seleccione un turno.");
            return "reservas/reserva_form";
        }
        // Autoasignar una de las mesas disponibles
        Optional<Reserva> reserva = reservaService.crearReserva(reservaForm);
        if (reserva.isPresent()) {
            // Éxito.
           return "redirect:/reservas/confirmacion";
        } else {
            // Fallo al asignar mesa.
            cargarDatosModelo(model, reservaForm, turnoService.obtenerTurnosDisponibles(reservaForm.getFecha(), reservaForm.getNumComensales()), true);
            model.addAttribute("mensaje", "No hay mesas disponibles para esa fecha y hora.");
            return "reservas/reservaForm";
        }
    }

    @GetMapping("/confirmación")
    public String confirmacionReserva(Model model) {
        model.addAttribute("mensaje", "Su Reserva ha sido procesada con Éxito!!!");
        return "reservas/confirmacion_reserva";
    }

}
