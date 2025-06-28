package com.grupo4.restaurante.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Importación necesaria
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data desde Lombok genera automaticamente getters, setters, toString
// @NoArgsConstructor y @AllArgsConstructor generan el constructor vacio y el que contiene todos los argumentos necesarios.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactoFormDTO {

    @NotBlank(message = "El nombre no puede estar vacio.")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
    private String nombre;

    @NotBlank(message = "El email no puede estar vacio.")
    @Email(message = "Por favor, introduce una dirección de email válida.")
    @Size(max = 255, message = "El email no puede exceder los 255 caracteres.")
    private String email;

    @NotBlank(message = "El asunto no puede estar vacio.")
    @Size(max = 200, message = "El asunto no puede exceder los 200 caracteres.")
    private String asunto;

    @NotBlank(message = "El mensaje no puede estar vacio.")
    @Size(max = 1000, message = "El mensaje no puede exceder los 1000 caracteres")
    private String mensaje;

    @NotNull(message = "Debes aceptar la política de privacidad para enviar tu mensaje.")
    @AssertTrue(message = "Debes aceptar la política de privacidad para enviar tu mensaje.")
    private Boolean aceptaPrivacidad; // CheckBox para consentimiento de la política de privacidad.

    private Boolean aceptaMarketing; // Checkbox para las comunicaciones de marketing.

}
