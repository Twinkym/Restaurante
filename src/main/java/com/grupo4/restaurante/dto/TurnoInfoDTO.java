package com.grupo4.restaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;


/**
 * Esta @Data es una anotación de conveniencia que
 * combina: @Getter, @Setter, @ToString, @EqualsAndHashCode
 * y @RequiredArgsConstructor, Excelente para un DTO como este.
 * El @AllArgsConstructor genera un constructor con un argumento
 * por campo de la clase, util para inicializar el DTO facil.
 */
@Data
@AllArgsConstructor
public class TurnoInfoDTO {
    private LocalTime hora;
    private boolean disponible;

    // Getters & Setters & ToString
}