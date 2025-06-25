package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "bebida")  // singular como es costumbre
@NoArgsConstructor
@AllArgsConstructor
public class Bebida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private double precio;
    private String imagenUrl; // Por si deseas mostrar imagen de la bebida en el frontend

    /**
     * Atributos relacionados con la entidad bebida, tipo bebida, stock
     */
    @Enumerated(EnumType.STRING)
    private TipoBebida tipo; // Por ejemplo: ALCOHOLICA, SIN_ALCOHOL, CALIENTE, FRIA

    private int stock;

    @ManyToMany(mappedBy = "bebidas")
    @ToString.Exclude
    private Set<Plato> platos; // Relación inversa con Plato
}
