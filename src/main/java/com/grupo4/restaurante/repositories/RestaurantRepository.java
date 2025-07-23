package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para acceder a la tabla de Restaurante.
 * - Proporciona métodos CRUD automáticos como: findAll(), findById(), save(), delete() y count(), etc.
 * - Este repositorio permite la interacción con la base de datos y así poder añadir nuevos, gestionar stocks
 * - al ofrecer la vinculación por Id de Restaurante permitiendo el intercambio de productos personal etc.,
 * - para operaciones relacionadas con la entidad {@link com.grupo4.restaurante.entities.Restaurante}.
 * - Para acceder a este repositorio se debe inyectar en un bean de Spring.
 * @author David De La Puente
 * @version 1.0
 * @since 2025-05-28
 *
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurante, Long>  {


}
