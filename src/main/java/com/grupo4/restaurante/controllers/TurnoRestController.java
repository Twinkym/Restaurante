package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.TurnoInfoDTO;
import com.grupo4.restaurante.services.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class TurnoRestController {

    @Autowired
    private TurnoService turnoService;

    @GetMapping("/turnos-info")
    public List<TurnoInfoDTO> getTurnosInfo(@RequestParam String fecha, @RequestParam int comensales) {
        LocalDate fechaLocal = LocalDate.parse(fecha);  //  Se puede Validar.
        return turnoService.obtenerTurnosDisponibles(fechaLocal, comensales);
    }
}
