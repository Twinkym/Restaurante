package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.UsuarioRegistroDTO;
import com.grupo4.restaurante.entities.Usuario;
import com.grupo4.restaurante.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/auth")
@AllArgsConstructor
public class RegistroController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/auth/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioRegistroDTO());
        return "auth/registro";
    }

    @PostMapping("/auth/registro")
    public String guardarUsuario(@ModelAttribute("usuario") @Valid UsuarioRegistroDTO usuarioRegistroDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "auth/registro";
        }

        // Validar nombre de usuario existente.
        if (usuarioRepository.existsByUsername(usuarioRegistroDTO.getUsername())) {
            model.addAttribute("error", "Ya existe un usuario con ese nombre.");
            return "auth/registro";
        }

        // Validar contraseñas iguales.
        if (!usuarioRegistroDTO.getPassword().equals(usuarioRegistroDTO.getConfirmPassword())) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "auth/registro";
        }

        if ("ADMIN".equalsIgnoreCase(usuarioRegistroDTO.getRol())) {
            model.addAttribute("error", "No puedes asignarte el rol ADMIN.");
            return "auth/registro";
        }

        // Crear y guardar usuario.
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(usuarioRegistroDTO.getUsername());
        nuevoUsuario.setNombre(usuarioRegistroDTO.getNombre());
        nuevoUsuario.setPassword(passwordEncoder.encode(usuarioRegistroDTO.getPassword()));
        nuevoUsuario.setEmail(usuarioRegistroDTO.getEmail());
        nuevoUsuario.setRol(usuarioRegistroDTO.getRol());

        usuarioRepository.save(nuevoUsuario);

        return "redirect:/login?registroExitoso";
    }
}
