package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

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
@Builder(toBuilder = true)
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numComensales;
    private String nombreCliente;
    // TODO add field in form
    private String telefono;
    private String email;
    private LocalDate fecha;
    private LocalTime hora;

    @ManyToOne
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @ManyToMany(fetch = FetchType.LAZY, // LAZY es buena práctica para ManyToMany para evitar cargas excesivas,
                cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // Qué operaciones se propagan.
    @JoinTable(
            name = "reserva_platos_solicitados", // Nombre de la tabla de unión en la DB
            joinColumns = @JoinColumn(name = "reserva_id"), // Columna que referencia a la ID de Reserva en la tabla de unión
            inverseJoinColumns = @JoinColumn(name = "plato_id") // Columna que referencia al ID de Plato en la tabla de unión
    )
    private Set<Plato> platosSolicitados = new HashSet<>(); // Usa Set para evitar duplicados y mejor rendimiento, o List si el orden importa

    // ==============================================
    // Métodos para añadir/eliminar platos (muy recomendables para relaciones @ManyToMany)
    // ==============================================
    public void addPlato(Plato plato) {
        this.platosSolicitados.add(plato);
        // Opcional: Si Plato también tiene una relación @ManyToMany bidireccional a Reserva
        // plato.getReservas().add(this);
    }

    public void removePlato(Plato plato) {
        this.platosSolicitados.remove(plato);
        // Opcional: Si Plato también tiene una relación @ManyToMany bidireccional a Reserva
        // plato.getReservas().remove(this);
    }

    /*
     * ... Getters y setters para todos los campos, incluyendo platosSolicitados
     */

}
