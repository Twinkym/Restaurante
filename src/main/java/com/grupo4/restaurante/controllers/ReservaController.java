package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.ReservaForm;
import com.grupo4.restaurante.dto.TurnoInfoDTO;
import com.grupo4.restaurante.entities.Reserva;
import com.grupo4.restaurante.services.MesaAsignadorService;
import com.grupo4.restaurante.services.ReservaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final MesaAsignadorService mesaAsignadorService;

    public ReservaController(ReservaService reservaService, MesaAsignadorService mesaAsignadorService) {
        this.reservaService = reservaService;
        this.mesaAsignadorService = mesaAsignadorService;
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("reservaForm", new ReservaForm());
        // TODO reservaService.obtenerCapacidadMaxima() la mayor capacidad maxima de las mesas
        model.addAttribute("capacidades", IntStream.rangeClosed(1, 8).boxed().toList());

        return "reservas/reserva_form";
    }

    @PostMapping("/guardar")
    public String procesarFormulario(@ModelAttribute ReservaForm reservaForm, Model model) {
        // Autoasignar una de las mesas disponibles
        Optional<Reserva> reserva = reservaService.crearReserva(reservaForm);

        // TODO Updatear mensaje logica de que mostrar despues
        if (reserva.isPresent()) {
            model.addAttribute("mensaje", "¡Reserva realizada con éxito!");
        } else {
            // TODO maybe cambiar por un se ha producido un error en la creacion de su reserva o algo asi
            model.addAttribute("mensaje", "No hay mesas disponibles para esa fecha y hora.");
        }

        return "reservas/reserva_form";
    }

    @GetMapping("/turnos-info")
    @ResponseBody
    public List<TurnoInfoDTO> obtenerTurnos(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("comensales") int comensales
    ) {
        return mesaAsignadorService.obtenerTurnosInfo(fecha, comensales);
    }
}
