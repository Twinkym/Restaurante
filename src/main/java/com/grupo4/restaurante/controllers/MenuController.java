package com.grupo4.restaurante.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/carne")
    public String mostrarMenuCarne(Model model) {
        model.addAttribute("titulo", "Menú de Carne");
        return "menus/carne";
    }

    @GetMapping("/pescado")
    public String mostrarMenuPescado(Model model) {
        model.addAttribute("titulo", "Menú de Pescado");
        return "menus/pescado";
    }

    @GetMapping("/vegetariano")
    public String mostrarMenuVegetariano(Model model) {
        model.addAttribute("titulo", "Menú Vegetariano");
        return "menus/vegetariano";
    }
}
