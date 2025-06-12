package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Mesa;
import com.grupo4.restaurante.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // TODO MEJORAR con logica de horas
    List<Reserva> findAllByFecha(LocalDate fecha);

    boolean existsByFechaAndMesa(LocalDate fecha, Mesa mesa);
}