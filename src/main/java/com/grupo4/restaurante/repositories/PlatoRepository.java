package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Plato;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {
    List<Plato> findByEsCaliente(boolean esCaliente);

}