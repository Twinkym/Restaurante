package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.entities.Plato;
import com.grupo4.restaurante.services.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.model.IAttribute;

import java.util.List;

/**
 * Menu Controller.
 * @author David De La Puente
 * @author Luis Miguel
 * @version 1.0
 * @since 2025-05-28
 *
 */

@Controller
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private PlatoService platoService;

    @GetMapping("/carne")
    public String menuCarne(Model model) {
        List<Plato> platosCarne = platoService.obtenerPlatosPorCategoria("carne");
        model.addAttribute("titulo", "Menú de Carne");
        model.addAttribute("platos", platosCarne);
        return "menus/carne";
    }

    @GetMapping("/pescado")
    public String menuPescado(Model model) {
        List<Plato> platosPescado = platoService.obtenerPlatosPorCategoria("pescado");
        model.addAttribute("titulo", "Menú de Pescado");
        model.addAttribute("platos", platosPescado);
        return "menus/pescado";
    }

    @GetMapping("/vegetariano")
    public String menuVegetariano(Model model){
        List<Plato> platoVegetariano = platoService.obtenerPlatosPorCategoria("vegetariano");
        model.addAttribute("titulo", "Menu vegetariano");
        model.addAttribute("platos", platoVegetariano);
        return "menus/vegetariano";
    }
}
