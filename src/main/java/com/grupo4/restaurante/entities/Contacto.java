package com.grupo4.restaurante.entities;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidad Contacto.
 * Representa un mensaje enviado desde el formulario de contacto del restaurante.
 * Se guarda en base de datos para consultas futuras por parte del administrador.
 * @author David De La Puente
 * @version 1.1
 * @since 2025-07-02
 */
@Builder
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {

    /*
     * Identificador único del contacto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Estas anotaciones en los campos @Column para que los campos no puedan estar vacíos
    private String nombre;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String asunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fecha;
}
