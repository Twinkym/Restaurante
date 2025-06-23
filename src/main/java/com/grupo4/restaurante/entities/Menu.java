package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
@Table(name = "menus")  // Por convención, los nombres de tabla van en minúscula y plural.
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre descriptivo del manú.
     * Ejemplo: "Menu del Día", "Menu Fin de Semana", "Menú Infantil".
     * Este campo es REQUERIDO por MenuRepository.
     */
    @Column(name = "nombre")
    private String nombre;

    /* --- Un menú probablemente también tendrá una lista de productos ---*/
    @ManyToMany
    @JoinTable(
            name = "menu_producto",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    @ToString.Exclude
    private List<Producto> productos;
}
