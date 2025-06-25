package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Bebida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Bebida.
 * Proporciona operaciones CRUD y consultas personalizadas.
 *
 * @author David
 * @version 1.0
 * @since 2025-06-24
 */
@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Long> {

    /**
     * Encuentra bebidas por nombre (contiene, insensible a mayúsculas).
     *
     * @param nombre Fragmento del nombre de la bebida.
     * @return Lista de bebidas que contienen ese nombre.
     */
    List<Bebida> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Encuentra bebidas por tipo (por ejemplo, refresco, vino, cerveza).
     *
     * @param tipo Tipo de bebida.
     * @return Lista de bebidas del tipo especificado.
     */
    List<Bebida> findByTipo(String tipo);

    /**
     * Encuentra todas las bebidas disponibles (stock mayor a cero).
     *
     * @return Lista de bebidas disponibles.
     */
    List<Bebida> findByStockGreaterThan(int stockMinimo);

    /**
     * Encuentra bebidas por rango de precio.
     *
     * @param precioMin Precio mínimo.
     * @param precioMax Precio máximo.
     * @return Lista de bebidas en ese rango de precio.
     */
    List<Bebida> findByPrecioBetween(Double precioMin, Double precioMax);
}
