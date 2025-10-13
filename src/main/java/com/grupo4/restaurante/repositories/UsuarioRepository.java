package com.grupo4.restaurante.repositories;

/*
 * Mostrar el formulario en /contacto(GET).
 * Procesar el envío (POST).
 * Validar el formulario con @Valid.
 * Guardar el mensaje como entidad Contacto.
 * Redirigir a una vista de confirmación o a la misma con errores.
 *
 * @author David De La Puente - KirgoDev
 * @version 1.1
 * @since 2025-09-06
 *
 */

import com.grupo4.restaurante.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByUsername(String username);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
}