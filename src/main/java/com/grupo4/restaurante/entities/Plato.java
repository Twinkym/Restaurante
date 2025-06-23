package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

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
@Table(name = "plato")
@NoArgsConstructor
@AllArgsConstructor
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private boolean esCaliente;
    private double precio;

    public Plato(String nombre, String descripcion, boolean esCaliente, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esCaliente = esCaliente;
        this.precio = precio;
    }
}