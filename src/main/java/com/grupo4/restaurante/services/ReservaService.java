package com.grupo4.restaurante.services;

import com.grupo4.restaurante.dto.ReservaFormDTO;
import com.grupo4.restaurante.entities.Mesa;
import com.grupo4.restaurante.entities.Reserva;
import com.grupo4.restaurante.repositories.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final MesaService mesaService;
    private final TurnoService turnoService;

    public Optional<Reserva> crearReserva(ReservaFormDTO form) {
        Optional<Mesa> mesaOpt = mesaService.encontrarMesaDisponible(form.getFecha(), form.getNumComensales(), form.getHoraTurno());

        if (mesaOpt.isEmpty()) return Optional.empty();

        Reserva reserva = Reserva.builder()
                .fecha(form.getFecha())
                .hora(form.getHoraTurno())
                .numComensales(form.getNumComensales())
                .nombreCliente(form.getNombreCliente())
                .telefono(form.getTelefono())
                .email(form.getEmailCliente())
                .mesa(mesaOpt.get())
                .build();

        return Optional.of(reservaRepository.save(reserva));
    }

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }

    public void deleteById(Long id) {
        reservaRepository.deleteById(id);
    }

    public Optional<Reserva> updateReserva(ReservaFormDTO reservaFormDTO) {
        Optional<Reserva> existingReservaOptional = reservaRepository.findById(reservaFormDTO.getId());

        if (existingReservaOptional.isEmpty()) {
            return Optional.empty(); //
        }

        Reserva existingReserva = existingReservaOptional.get();

        boolean needTableReassignment = !existingReserva.getFecha().equals(reservaFormDTO.getFecha()) ||
                                        !existingReserva.getHora().equals(reservaFormDTO.getHoraTurno()) ||
                                         existingReserva.getNumComensales() != reservaFormDTO.getNumComensales();

        if (needTableReassignment) {
            Optional<Mesa> availableMesa = mesaService.encontrarMesaDisponible( reservaFormDTO.getFecha(), reservaFormDTO.getNumComensales(), reservaFormDTO.getHoraTurno());

            if (availableMesa.isEmpty()) {
                return Optional.empty();
            }
            existingReserva.setMesa(availableMesa.get());
        }

        existingReserva.setNumComensales(reservaFormDTO.getNumComensales());
        existingReserva.setNombreCliente(reservaFormDTO.getNombreCliente());
        existingReserva.setTelefono(reservaFormDTO.getTelefono());
        existingReserva.setEmail(reservaFormDTO.getEmailCliente());
        existingReserva.setFecha(reservaFormDTO.getFecha());
        existingReserva.setHora(reservaFormDTO.getHoraTurno());

        return Optional.of(reservaRepository.save(existingReserva));
    }
}
