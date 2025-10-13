package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Entidad Cliente.
 * @author David De La Puente
 * @author Luis Miguel
 * @version 1.0
 * @since 2025-05-28
 *
 */
@Builder
@Getter
@Setter
@Entity
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Boolean activo;

    public Collection<Object> getReservas() {
        return Collections.emptyList();
    }

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombre = " + nombre + ", " +
                "apellido = " + apellido + ", " +
                "email = " + email + ", " +
                "telefono = " + telefono + ", " +
                "activo = " + activo + ")";
    }

}

