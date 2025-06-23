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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "mesa")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private Integer capacidad;

    // En caso de que por cualquier circumstance alguna de las mesas puedan estar no disponibles en ese momento
    // podríamos configurar una relation de fecha-disponible de modo que para días en concreto podemos
    // deshabilitar una mesa, a lo mejor que tenga disponible que se comparara primero para casuistic que no sabemos
    // en qué momento volvería a estar disponible y disponible-hora-fecha en cuyo caso es para turnos / fechas específicas
    private Boolean disponible;
}
