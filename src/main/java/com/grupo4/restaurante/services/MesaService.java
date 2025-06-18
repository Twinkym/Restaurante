package com.grupo4.restaurante.services;

import com.grupo4.restaurante.dto.TurnoInfoDTO;
import com.grupo4.restaurante.entities.Mesa;
import com.grupo4.restaurante.entities.Reserva;
import com.grupo4.restaurante.repositories.MesaRepository;
import com.grupo4.restaurante.repositories.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;
    private final ReservaRepository reservaRepository;

    public MesaService(MesaRepository mesaRepository, ReservaRepository reservaRepository) {
        this.mesaRepository = mesaRepository;
        this.reservaRepository = reservaRepository;
    }
    // Obtener turnos con mesas disponibles para una fecha determinada y un numero de personas
    public List<TurnoInfoDTO> obtenerTurnosInfo(LocalDate fecha, int numeroPersonas) {
        // TODO Mover a base de datos los turnos y no hardcodearlo aqui
        // TODO incluso aunque no sea en base de datos moverlo a una const global para mandar tambien al front
        List<LocalTime> turnos = List.of(
                LocalTime.of(13, 0),
                LocalTime.of(13, 30),
                LocalTime.of(14, 0),
                LocalTime.of(14, 30),
                LocalTime.of(15, 0),
                LocalTime.of(15, 30)
        );

        // Sacamos las mesas que nos valdrian en funcion del numero de personas
        // TODO agregar la logica de asignacion de mesas ajustadas y encapsularla puesto que se repite
        List<Mesa> mesasAdecuadas = mesaRepository.findByCapacidadGreaterThanEqualAndDisponibleTrue(numeroPersonas);
        // TODO COMIDA CENA FILTRO SI SE IMPLEMENTA
        // Sacar las reserva para el dia
        List<Reserva> reservasDelDia = reservaRepository.findAllByFecha(fecha);

        List<TurnoInfoDTO> resultado = new ArrayList<>();

        for (LocalTime turno : turnos) {
            boolean disponible = hayMesaDisponibleEnTurno(mesasAdecuadas, reservasDelDia, turno);
            resultado.add(new TurnoInfoDTO(turno, disponible));
        }

        return resultado;
    }

    private boolean hayMesaDisponibleEnTurno(List<Mesa> mesas, List<Reserva> reservas, LocalTime turno) {
        return encontrarMesaDisponibleEnLista(mesas, reservas, turno).isPresent();
    }

    // TODO LOGICA DE ASIGNACION MAS AJUSTADA
    /*private int calcularCapacidadMinima(int comensales) {
        if (comensales <= 2) return 2;
        if (comensales <= 4) return 4;
        if (comensales <= 6) return 6;
        return 8;
    }*/

    public Optional<Mesa> encontrarMesaDisponible(LocalDate fecha, int comensales, LocalTime hora) {
        // TODO logica de asignacion ajustada para las mesas filtro mas extenso
        List<Mesa> mesas = mesaRepository.findByCapacidadGreaterThanEqualAndDisponibleTrue(comensales);
        List<Reserva> reservas = reservaRepository.findAllByFecha(fecha);

        return encontrarMesaDisponibleEnLista(mesas, reservas, hora);
    }

    private Optional<Mesa> encontrarMesaDisponibleEnLista(List<Mesa> mesas, List<Reserva> reservas, LocalTime hora) {
        return mesas.stream()
                .sorted(Comparator.comparingInt(Mesa::getCapacidad))
                .filter(mesa -> estaDisponible(mesa, reservas, hora))
                .findFirst();
    }

    private boolean estaDisponible(Mesa mesa, List<Reserva> reservas, LocalTime horaInicio) {
        return reservas.stream()
                .filter(r -> r.getMesa().getId().equals(mesa.getId()))
                .noneMatch(reservaExistente -> seSolapan(horaInicio, reservaExistente.getHora()));
    }

    // TODO se solapa solamente si es despues por 90 mins
    private boolean seSolapan(LocalTime inicio1, LocalTime inicio2) {
        // 89 paera que no falle en las enpunto)
        LocalTime fin1 = inicio1.plusMinutes(89);
        LocalTime fin2 = inicio2.plusMinutes(89);

        return inicio1.isBefore(fin2) && inicio2.isBefore(fin1);
    }
}
