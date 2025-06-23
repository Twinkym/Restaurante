package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

/*
 * Entidad que representa la información del propio restaurante.
 * Se espera que en la BBDD exista una unica fila en esta tabla,
 * conteniendo los datos del establecimiento que utiliza la aplicación.
 */

/**
 * Entidad Empleado.
 * @author David De La Puente
 * @author Luis Miguel
 * @version 1.0
 * @since 2025-05-28
 *
 */
@Builder    // Patron de diseño Builder para crear objetos (Lombok).
@Getter     // Genera todos los getters (Lombok).
@Setter     // Genera todos los Setters (Lombok).
@ToString   // Genera la función toString() para debugging (Lombok).
@Entity     // Informa a JPA que esta clase corresponde a una tabla en la base de datos.
@Table(name = "restaurante")  // Especifica nombre de la tabla en la BBDD.
@NoArgsConstructor      // Genera un constructor vació.
@AllArgsConstructor     // Genera un constructor con todos los argumentos (Lombok).
public class Restaurante {

    /*
     *  Identificador único del restaurante.
     * Aunque solo habrá un restaurante, es una buena práctica tener un ID.
     */
    @Id // Marca el campo id como la clave primaria. (primary key).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la generación automática del ID. // IDENTITY delega la tarea a la BBDD(auto-incremento).
    private Long id;

    /*
     *  El nombre comercial del restaurante. Este campo es obligatorio.
     * Ejemplo: "Comida Española"
     */
    @Column(nullable = false) // Permite configurar la columna de la BBDD.  // nullable = false, hace que el campo sea obligatorio.
    private String nombre;

    @Column
    private String direccion;

    /*
     *  Número de teléfono de contacto y reservas.
     */
    @Column
    private String telefono;

    /*
     *  Número de Identificación Fiscal (NIF/CIF) del restaurante.
     * Debería ser único, por eso añadimos la restricción `unique = true`.
     */
    @Column(unique = true) // Asegura que no haya dos filas con el mismo valor en esta columna. Así al generarse los Id se verán numeros diferentes y "Correlativos sin olvidar que cuando se borra un valor este permanece en una especie de caché".
    private String nif;

    /*
     *  Correo electrónico de contacto.
     */
    @Column
    private String email;

    /*
     *  Página web del restaurante (opcional).
     */
    @Column(name = "sitio_Web")  // Especificamos el nombre de la columna en la BD.
    private String sitioWeb;

    /*
     *  Horario de apertura del restaurante, como texto libre.
     * Ejemplo: "L-V: 13:00-16:00, 20:00-23:00. S-D: 13:00-23:00"
     */
    @Column
    private String horario;

}
