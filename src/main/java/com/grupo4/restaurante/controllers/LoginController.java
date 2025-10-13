package com.grupo4.restaurante.controllers;

/*
 * Mostrar el formulario en /login(GET).
 * Procesar el envío (POST).
 * Validar el formulario con @Valid.
 * Guardar el mensaje como entidad Contacto.
 * Redirigir a una vista de confirmación o a la misma con errores.
 *
 * @author David De La Puente - KirgoDev
 * @version 1.1
 * @since 2025-09-06
 *
 */

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/auth/login")
    public String redireccionarLoginObsoleto() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("titulo", "Iniciar Sesión");
        return "auth/login";
    }

    @GetMapping("/login-success")
    public String redirigirPorRol() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        for (GrantedAuthority authority : auth.getAuthorities()) {
            String rol = authority.getAuthority();
            switch (rol) {
                case "ROLE_CLIENTE" -> {
                    return "redirect:/dashboard/cliente";
                }
                case "ROLE_EMPLEADO" -> {
                    return "redirect:/dashboard/empleado";
                }
                case "ROLE_GERENTE" -> {
                    return "redirect:/dashboard/gerente";
                }
                case "ROLE_ADMIN" -> {
                    return "redirect:/dashboard/admin";
                }
            }
        }
        return "redirect:/login?error";
    }
}
