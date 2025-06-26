package com.grupo4.restaurante.services;

import com.grupo4.restaurante.entities.Plato;
import com.grupo4.restaurante.repositories.PlatoRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Service
public class PlatoService {
    @Autowired
    private PlatoRepository platoRepository;

    public List<Plato> obtenerPlatosPorCategoria(String categoria) {
        return platoRepository.findByCategoriaIgnoreCase(categoria);
    }

}
