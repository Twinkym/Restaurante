package com.grupo4.restaurante.dto;


/*
 * DTO para el formulario de contacto.
 * Se utiliza para transferir los datos del formulario con validaciones.
 * Separación de lógica: el DTO viaja entre la vista y el controlador, la entidad se encarga solo de la persistencia.
 *
 * @author David De La Puente
 * @version 1.1
 * @since 2025-07-02
 */

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactoDTO {

    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "Debe ser un email válido.")
    private String email;

    @NotBlank(message = "El asunto es obligatorio.")
    private String asunto;

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;
}