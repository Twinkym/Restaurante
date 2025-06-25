package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private boolean esCaliente;
    private double precio;

    /**
     * Relación bidireccional con Bebida.
     * Un plato puede tener muchas bebidas asociadas y viceversa.
     */
    @ManyToMany
    @JoinTable(
            name = "plato_bebida", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "plato_id"), // FK hacia esta entidad
            inverseJoinColumns = @JoinColumn(name = "bebida_id") // FK hacia la otra entidad
    )
    @ToString.Exclude private Set<Bebida> bebidas;

    public Plato(String nombre, String descripcion, boolean esCaliente, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esCaliente = esCaliente;
        this.precio = precio;
    }
}