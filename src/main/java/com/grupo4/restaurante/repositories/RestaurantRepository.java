package com.grupo4.restaurante.repositories;

import com.grupo4.restaurante.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurante, Long>  {


}
