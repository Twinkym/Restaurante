package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.UsuarioRegistroDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("pagina", new com.grupo4.restaurante.dto.PaginaDTO("Registro"));
        model.addAttribute("usuario", new UsuarioRegistroDTO());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("usuario") UsuarioRegistroDTO usuarioRegistroDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pagina", new com.grupo4.restaurante.dto.PaginaDTO("Registro"));
            return "auth/registro";
        }

        // Simulación: En producción guardar en base de datos.
        log.info("Usuario registrado: {}", usuarioRegistroDTO.getUsername());
        log.info("Password (encriptado): {}", passwordEncoder.encode(usuarioRegistroDTO.getPassword()));
        model.addAttribute("mensaje", "¡Registro exitoso!");
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("pagina", new com.grupo4.restaurante.dto.PaginaDTO("Iniciar Sesión"));
        return "auth/login";
    }
}
