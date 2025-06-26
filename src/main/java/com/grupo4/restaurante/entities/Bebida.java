package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "bebida")
@NoArgsConstructor
@AllArgsConstructor
public class Bebida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private double precio;
    private String imagenUrl;

    @Enumerated(EnumType.STRING)
    private TipoBebida tipo;

    private int stock;

    @ManyToMany
    @JoinTable(
            name = "bebida_plato",
            joinColumns = @JoinColumn(name = "bebida_id"),
            inverseJoinColumns = @JoinColumn(name = "plato_id")
    )
    @ToString.Exclude
    private List<Plato> platos;
}
