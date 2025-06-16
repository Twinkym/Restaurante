package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad Pedido.
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
@Table(name = "Pedidos")
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
