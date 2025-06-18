package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    // TODO podriamos buscar por greater than equal y lower than equal en caso de que queramos buscar directamente
    // la logica de como asignamos las mesas estilo a 1 y 2 puede de 2, 3 y 4 de 4....
    List<Mesa> findByCapacidadGreaterThanEqualAndDisponibleTrue(int capacidadMinima);
}

