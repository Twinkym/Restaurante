package com.grupo4.restaurante.services;

import com.grupo4.restaurante.dto.GerenteDashboardDTO;
import org.springframework.stereotype.Service;

@Service
public class GerenteDashboardService {

    public GerenteDashboardDTO obtenerDatosDashboardDTO(String username){
        // Simulación de datos reales.

        GerenteDashboardDTO dto = new GerenteDashboardDTO();
        dto.setNombre("Gerente " + username);
        dto.setTotalClientes(53);
        dto.setTotalReservas(123);
        return dto;
    }

}
