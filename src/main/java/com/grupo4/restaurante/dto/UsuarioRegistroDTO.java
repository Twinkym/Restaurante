package com.grupo4.restaurante.dto;

/*
 * Mostrar el formulario en /contacto(GET).
 * Procesar el envío (POST).
 * Validar el formulario con @Valid.
 * Guardar el mensaje como entidad Contacto.
 * Redirigir a una vista de confirmación o a la misma con errores.
 *
 * @author David De La Puente - KirgoDev
 * @version 1.1
 * @since 2025-09-06
 *
 */

import com.grupo4.restaurante.entities.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
public class UsuarioRegistroDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio.")
    private String username;

    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotBlank(message = "El campo repetir contraseña es obligatorio.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String confirmPassword;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    private String email;

    private Rol rol = Rol.CLIENTE;  //Valor por defecto.

}
