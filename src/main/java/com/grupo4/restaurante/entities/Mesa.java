package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad Mesa.
 * @author David De La Puente
 * @author Alejandro
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
@Table(name = "Mesas")
@NoArgsConstructor
@AllArgsConstructor

public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")  // Es una buena práctica especificar el nombre de la columna.
    private String nombre;
}

