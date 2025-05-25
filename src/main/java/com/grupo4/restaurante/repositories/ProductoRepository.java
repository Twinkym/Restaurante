package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    //
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
