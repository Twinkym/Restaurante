package com.grupo4.restaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TurnoInfoDTO {
    private LocalTime hora;
    private boolean disponible;
}