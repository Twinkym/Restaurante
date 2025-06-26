package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

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
    private String categoria; // carne, pescado, vegetariano
    private double precio;
    private String imagenUrl;

    @ManyToMany(mappedBy = "platos")
    @ToString.Exclude
    private List<Bebida> bebidas;


    // Getters y setters
}

