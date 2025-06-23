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
    private final MesaService mesaService;

    public ReservaService(MesaService mesaService, ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
        this.mesaService = mesaService;
    }

    public Optional<Reserva> crearReserva(ReservaForm form) {
        Optional<Mesa> mesaOpt = mesaService.encontrarMesaDisponible(form.getFecha(), form.getNumComensales(), form.getHoraTurno());

        if (mesaOpt.isEmpty()) return Optional.empty();

        Reserva reserva = Reserva.builder()
                .fecha(form.getFecha())
                .hora(form.getHoraTurno())
                .numComensales(form.getNumComensales())
                .mesa(mesaOpt.get())
                .build();

        return Optional.of(reservaRepository.save(reserva));
    }


}
