package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para acceder a la tabla de Categorías.
 * - Proporciona métodos CRUD automáticos como: findAll(), findById(), save(), delete() y count(), etc.
 * - Este repositorio permite la interacción con la base de datos
 *      para operaciones relacionadas con la entidad {@link Categoria}.
 *      Para acceder a este repositorio se debe inyectar en un bean de Spring.
 * @author David De La Puente
 * @author Lara
 * @author Luis Miguel
 * @version 1.0
 * @since 2025-05-28
 *
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByNombreContainingIgnoreCase(String nombre);
}
