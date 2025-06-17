package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.entities.Mesa;
import com.grupo4.restaurante.repositories.MesaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminMesaController {

    private final MesaRepository mesaRepository;

    @GetMapping("/admin/mesas")
    public String findAll(Model model) {

        List<Mesa> mesas = mesaRepository.findAll();

        model.addAttribute("mesas", mesas);

        return "admin/mesa-list";
    }
}
