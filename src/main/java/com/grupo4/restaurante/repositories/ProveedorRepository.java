package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para acceder a la tabla de Categorías.
 * - Proporciona métodos CRUD automáticos como: findAll(), findById(), save(), delete() y count(), etc.
 * - Este repositorio permite la interacción con la base de datos
 *      para operaciones relacionadas con la entidad {@link com.grupo4.restaurante.entities.Proveedor}.
 *      Para acceder a este repositorio se debe inyectar en un bean de Spring.
 * @author David De La Puente
 * @author Angel
 * @author Luis Miguel
 * @version 1.0
 * @since 2025-05-28
 *
 */
@Repository
public interface ProveedorRepository  extends JpaRepository<Proveedor, Long>  {
    List<Proveedor> findByNombreContainingIgnoreCase(String nombre);
}
