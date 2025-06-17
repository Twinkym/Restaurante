package com.grupo4.restaurante.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Proveedor.
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
@ToString(exclude = "productos")
@Entity
@Table(name = "proveedores")
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String email;
    private String telefono;
    private Boolean activo;

    @ManyToMany()
    @JoinTable(name = "proveedor_producto")
    @Builder.Default       // Inicializa el atributo con una lista vacía.
    private List<Producto> productos = new ArrayList<>();


}
