package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TurnoRepository  extends JpaRepository<Turno, Long> {

    List<Turno> findByFechaAndCapacidadGreaterThanEqual(LocalDate fecha, int capacidad);
}
