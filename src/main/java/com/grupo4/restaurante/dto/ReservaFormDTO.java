package com.grupo4.restaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@NoArgsConstructor  // Necesario para Spring MVC pueda instanciarlo con @ModelAttribute
@AllArgsConstructor
public class ReservaFormDTO {
    private Long id; // Este campo es necesario para editar las reservas existentes.

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;
    private Integer numComensales;  // Usamos Integer para permitir null al inicio.
    private LocalTime horaTurno;    // Para almacenar el turno seleccionado.
    private String nombreCliente;
    private String telefono;
    private String emailCliente;
    // Aún se podrían incluir otros campos en caso de ser necesario como él, id de mesa, menú, etc.
}