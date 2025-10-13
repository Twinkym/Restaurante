package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.ClienteDashboardDTO;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Data
public class ClienteDashboardController {

    @GetMapping("/dashboard/cliente")
    public String mostrarDashboardCliente(Model model, Authentication auth) {
        ClienteDashboardDTO dto = new ClienteDashboardDTO();
        dto.setNombre(auth.getName());

        model.addAttribute("titulo", "Panel del Cliente");
        model.addAttribute("usuario", dto.getNombre());
        model.addAttribute("datos", dto);

        return "dashboard/cliente";
    }
}


