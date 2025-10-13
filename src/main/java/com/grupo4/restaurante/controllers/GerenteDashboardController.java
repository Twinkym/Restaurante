package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.GerenteDashboardDTO;
import com.grupo4.restaurante.services.GerenteDashboardService;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Builder
@Getter
@Setter
@Controller
@RequestMapping("/dashboard/gerente")
@RequiredArgsConstructor
public class GerenteDashboardController {

    private final GerenteDashboardService gerenteDashboardService;  // Recordar realizar la inyección del servicio en este caso o la clase para poder usarla en la función más adelante.

    @GetMapping("/dashboard/gerente")  // Mapeado de la ruta o enrutamiento de la clase gerente
    public String mostrarDashboardGerente(Model model, Authentication auth) {
        String username = auth.getName();
        GerenteDashboardDTO dto = gerenteDashboardService.obtenerDatosDashboardDTO(username);
        model.addAttribute("datos", dto);
        model.addAttribute("titulo", "Panel del Gerente");
        model.addAttribute("tituloCabecera", "Panel de Gestión Gerente");
        return "dashboard/gerente";
    }
}
