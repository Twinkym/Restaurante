package com.grupo4.restaurante.services;

import com.grupo4.restaurante.dto.ReservaForm;
import com.grupo4.restaurante.entities.Mesa;
import com.grupo4.restaurante.entities.Reserva;
import com.grupo4.restaurante.repositories.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final MesaAsignadorService mesaAsignadorService;

    public ReservaService(MesaAsignadorService mesaAsignadorService, ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
        this.mesaAsignadorService = mesaAsignadorService;
    }

    public Optional<Reserva> crearReserva(ReservaForm form) {
        Optional<Mesa> mesaOpt = mesaAsignadorService.encontrarMesaDisponible(form.getFecha(), form.getComensales(), form.getHora());

        if (mesaOpt.isEmpty()) return Optional.empty();

        Reserva reserva = Reserva.builder()
                .fecha(form.getFecha())
                .hora(form.getHora())
                .numComensales(form.getComensales())
                .mesa(mesaOpt.get())
                .build();

        return Optional.of(reservaRepository.save(reserva));
    }


}
