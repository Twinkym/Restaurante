package com.grupo4.restaurante.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Builder
@Getter
@Setter
@Controller
@AllArgsConstructor
public class AdminDashboardController {

    @GetMapping("/dashboard/admin")
    public String mostrarDashboardAdmin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("titulo", "Panel del Admin");
        model.addAttribute("usuario", auth.getName());
        return "dashboard/admin";

    }
}
