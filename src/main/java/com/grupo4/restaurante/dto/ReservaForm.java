package com.grupo4.restaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor  // Necesario para Spring MVC pueda instanciarlo con @ModelAttribute
@AllArgsConstructor
public class ReservaForm {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;
    private Integer numComensales;  // Usamos Integer para permitir null al inicio.
    private LocalTime horaTurno;    // Para almacenar el turno seleccionado.
    private String nombreCliente;
    private String emailCliente;
    // Aún se podrían incluir otros campos en caso de ser necesario como el, id de mesa, menú, etc.
}