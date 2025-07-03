package com.grupo4.restaurante.repositories;


import com.grupo4.restaurante.entities.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Contacto.
 */
@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

}
