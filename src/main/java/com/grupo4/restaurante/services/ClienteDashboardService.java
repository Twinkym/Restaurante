package com.grupo4.restaurante.services;

import com.grupo4.restaurante.dto.ClienteDashboardDTO;
import com.grupo4.restaurante.entities.Cliente;
import com.grupo4.restaurante.repositories.ClienteRepository;
import lombok.Data;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@Service
public class ClienteDashboardService {

    private final ClienteRepository clienteRepository;

    public ClienteDashboardDTO obtenerDatosDashboardDTO(String username) {
        Cliente cliente = clienteRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Cliente no encontrado"));

        ClienteDashboardDTO dto = new ClienteDashboardDTO();
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        dto.setTotalReservas(cliente.getReservas().size());
        return dto;
    }
}
