package com.grupo4.restaurante.dto;

import lombok.*;

// Solamente almacena y sirve datos, no incluir de ningún modo lógica.
@Data
public class ClienteDashboardDTO {

    private String nombre;
    private String email;
    private int totalReservas;
}
