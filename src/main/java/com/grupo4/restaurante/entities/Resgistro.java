package com.grupo4.restaurante.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Resgistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
