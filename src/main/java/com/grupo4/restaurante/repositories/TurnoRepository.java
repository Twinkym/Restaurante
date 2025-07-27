package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio JPA para acceder a la tabla de Turnos.
 * - Proporciona métodos CRUD automáticos como: findAll(), findById(), save(), delete() y count(), etc.
 * - Este repositorio permite la interacción con la base de datos y así poder gestionar los turnos de comidas,
 * - personal,
 * - para operaciones relacionadas con la entidad {@link com.grupo4.restaurante.entities.Turno}.
 * - Para acceder a este repositorio se debe inyectar en un bean de Spring.
 * @author David De La Puente
 * @version 1.0
 * @since 2025-05-28
 *
 */
@Repository
public interface TurnoRepository  extends JpaRepository<Turno, Long> {

    List<Turno> findByFechaAndCapacidadGreaterThanEqual(LocalDate fecha, int capacidad);
}
