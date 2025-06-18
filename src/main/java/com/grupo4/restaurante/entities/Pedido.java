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
import java.time.LocalDateTime;

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

    /**
     * Identificador textual o nombre del pedido.
     * Ejemplo: "Pedido Cliente Mesa 5", "Pedido para llevar #102".
     * Este campo es REQUERIDO por PedidoRepository para las búsquedas.
     */
    @Column(name = "nombre")
    private String nombre;

    // --- Otros campos probables para un Pedido (ejemplos) ---
    @ManyToOne
    private Mesa mesa;

    @ManyToOne
    private Cliente cliente;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaPedido;

    private Double total;
}
