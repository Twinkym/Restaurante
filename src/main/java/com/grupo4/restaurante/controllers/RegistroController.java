package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.UsuarioRegistroDTO;
import com.grupo4.restaurante.entities.Usuario;
import com.grupo4.restaurante.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistroController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String guardarUsuario(@ModelAttribute("usuario") @Valid UsuarioRegistroDTO usuarioRegistroDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "registro";
        }

        if (usuarioRepository.existsByUsername(usuarioRegistroDTO.getUsername())) {
            model.addAttribute("error", "Ya existe un usuario con ese nombre.");
            return "registro";
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(usuarioRegistroDTO.getUsername());
        nuevoUsuario.setPassword(passwordEncoder.encode(usuarioRegistroDTO.getPassword()));
        nuevoUsuario.setEmail(usuarioRegistroDTO.getEmail());
        nuevoUsuario.setRol(usuarioRegistroDTO.getRol());

        usuarioRepository.save(nuevoUsuario);

        return "redirect:/login?registroExitoso";
    }
}
