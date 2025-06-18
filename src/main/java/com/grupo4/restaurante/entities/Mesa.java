package com.grupo4.restaurante.entities;

import jakarta.persistence.*;
import lombok.*;

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

    // TODO podria ser  string NOMBRE / STRING IDENTIFICADOR
    private Integer numero;
    private Integer capacidad;

    // En caso de que por cualquier circustancia alguna de las mesas puedan estar no disponibles en ese momento
    // TODO podriamos configurar una relacion de fecha-disponible de modo que para dias en concreto podemos
    // deshabilitar una mesa, alomejor que tenga disponible que se comparara primero para casuisticas que no sabemos
    // en que momento volvera a estar disponible y disponible-hora-fecha en cuyo caso es para turnos / fechas especificas
    private Boolean disponible;
}
