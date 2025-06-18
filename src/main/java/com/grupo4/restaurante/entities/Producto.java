package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad Producto.
 * @author David De La Puente
 * @author Alejandro
 * @author Luis Miguel
 * @version 1.0
 * @since 2025-05-28
 *
 */
@Builder
@Setter
@Getter
@Entity
@Table(name = "productos")
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private int stock;
    private String imagen;
    private boolean disponible;

    // Getters, Setters y constructor vacío.

    public Producto(String nombre, String descripcion, Double precio, int stock, String imagen, boolean disponible) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", imagen='" + imagen + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}
