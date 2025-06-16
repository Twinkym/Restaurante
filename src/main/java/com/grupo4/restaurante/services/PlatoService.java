package com.grupo4.restaurante.services;

import com.grupo4.restaurante.entities.Plato;
import com.grupo4.restaurante.repositories.PlatoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatoService {

    private final PlatoRepository platoRepository;

    public PlatoService(PlatoRepository platoRepository) {
        this.platoRepository = platoRepository;
    }

    public List<Plato> obtenerPlatosCalientes() {
        return platoRepository.findByEsCaliente(true);
    }

    public List<Plato> obtenerPlatosFrios() {
        return platoRepository.findByEsCaliente(false);
    }
}