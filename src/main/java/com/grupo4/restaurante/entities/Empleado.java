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
@Table(name = "empleados")
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Empleado empleado;
}
