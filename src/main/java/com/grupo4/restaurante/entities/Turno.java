package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/*
 * Entidad que representa la información del propio restaurante.
 * Se espera que en la BBDD exista una unica fila en esta tabla,
 * conteniendo los datos del establecimiento que utiliza la aplicación.
 */

/**
 * Entidad Empleado.
 * @author David De La Puente
 * @author Luis Miguel
 * @version 1.0
 * @since 2025-05-28
 *
 */
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "turno")
@NoArgsConstructor
@AllArgsConstructor
public class Turno {

    /*
     * Identificador único del turno.
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hora;

    private boolean disponible;

    private LocalDate fecha;

    private int capacidad;

    // Getters & Setters & toString generados por lombok.
}
