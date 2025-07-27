package com.grupo4.restaurante.repositories;


import com.grupo4.restaurante.entities.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Contacto.
 * Proporciona operaciones para el envio de emails.
 *
 * @author David
 * @version 1.0
 * @since 2025-07-23
 */
@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

}
