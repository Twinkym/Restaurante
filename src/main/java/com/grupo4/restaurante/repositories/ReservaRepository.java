package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Mesa;
import com.grupo4.restaurante.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio JPA para acceder a la tabla de Reservas.
 * - Proporciona métodos CRUD automáticos como: findAll(), findById(), save(), delete() y count(), etc.
 * - Este repositorio permite la interacción con la base de datos
 * - para operaciones relacionadas con la entidad {@link com.grupo4.restaurante.entities.Menu}.
 * - Para acceder a este repositorio se debe inyectar en un bean de Spring.
 * @author David De La Puente
 * @author Luis Miguel
 * @version 1.0
 * @since 2025-05-28
 *
 */
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findAllByFecha(LocalDate fecha);

    boolean existsByFechaAndMesa(LocalDate fecha, Mesa mesa);
}