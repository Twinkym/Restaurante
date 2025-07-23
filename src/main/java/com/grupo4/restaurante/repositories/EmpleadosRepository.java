package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para acceder a la tabla de Empleados.
 * - Proporciona métodos CRUD automáticos como: findAll(), findById(), save(), delete() y count(), etc.
 * - Este repositorio permite la interacción con la base de datos
 *      para operaciones relacionadas con la entidad {@link com.grupo4.restaurante.entities.Empleado}.
 *      Para acceder a este repositorio se debe inyectar en un bean de Spring.
 *
 * @author David
 * @version 1.0
 * @since 2025-06-24
 */
@Repository
public interface EmpleadosRepository extends JpaRepository<Empleado, Long> {

    List<Empleado> findByNombreContainingIgnoreCase(String nombre);
}
