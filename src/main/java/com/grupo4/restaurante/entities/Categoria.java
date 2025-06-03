package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "categorias")
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    private String descripcion;
    private Boolean activo;
}

