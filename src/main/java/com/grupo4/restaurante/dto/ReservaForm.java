package com.grupo4.restaurante.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservaForm {

    private LocalDate fecha;
    private LocalTime hora;
    private int comensales;
}