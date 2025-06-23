// src/main/java/com/grupo4/restaurante/services/TurnoService.java
package com.grupo4.restaurante.services;

import com.grupo4.restaurante.dto.TurnoInfoDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService {

    public List<TurnoInfoDTO> obtenerTurnosDisponibles(LocalDate fecha, int comensales) {
        List<TurnoInfoDTO> turnosDisponibles = new ArrayList<>();
        LocalTime horaInicio = LocalTime.of(19, 0);
        LocalTime horaFin = LocalTime.of(23, 0);

        for (LocalTime t = horaInicio; t.isBefore(horaFin.plusMinutes(1)); t = t.plusHours(1)) {
            // Asumimos que el turno está disponible por defecto.
            boolean disponible = (!fecha.isEqual(LocalDate.now()) || !t.equals(LocalTime.of(20, 0))) && (comensales <= 6 || !t.equals(LocalTime.of(19, 0)));
            // Añadir el turno con su estado de disponibilidad final
            turnosDisponibles.add(new TurnoInfoDTO(t, disponible));
        }
        return turnosDisponibles;
    }
}