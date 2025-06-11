package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.entities.Plato;

import com.grupo4.restaurante.services.PlatoService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platos")
public class PlatoController {

    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    @GetMapping("/calientes")
    public List<Plato> getPlatosCalientes() {
        return platoService.obtenerPlatosCalientes();
    }

    @GetMapping("/frios")
    public List<Plato> getPlatosFrios() {
        return platoService.obtenerPlatosFrios();
    }
}