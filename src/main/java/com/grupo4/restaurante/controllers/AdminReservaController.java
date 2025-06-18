package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.entities.Mesa;
import com.grupo4.restaurante.entities.Reserva;
import com.grupo4.restaurante.repositories.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminReservaController {

    private final ReservaRepository reservaRepository;

    @GetMapping("/admin/reservas")
    public String findAll(Model model) {

        List<Reserva> reservas = reservaRepository.findAll();

        model.addAttribute("reservas", reservas);

        return "admin/reserva-list";
    }
}
