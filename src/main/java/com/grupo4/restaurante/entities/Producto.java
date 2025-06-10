package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
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

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Producto(String nombre, String descripcion, Double precio, int stock, Categoria categoria, String imagen, boolean disponible) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    /*@Override
     *public String toString() {
     *    return "Producto{" +
     *            "id=" + id +
     *            ", nombre='" + nombre + '\'' +
     *            ", descripcion='" + descripcion + '\'' +
     *            ", precio=" + precio +
     *            ", stock=" + stock +
     *            ", imagen='" + imagen + '\'' +
     *            ", disponible=" + disponible +
     *            ", categorias=" + categoria +
     *            '}';
     * }
     */
}
