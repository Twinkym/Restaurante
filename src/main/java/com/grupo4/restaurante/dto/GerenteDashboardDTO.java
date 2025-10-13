package com.grupo4.restaurante.dto;

import lombok.Data;

@Data
public class GerenteDashboardDTO {
    private String nombre;
    private int totalReservas;
    private int totalClientes;
}
