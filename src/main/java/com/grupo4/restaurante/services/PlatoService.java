package com.grupo4.restaurante.services;

import com.grupo4.restaurante.repositories.PlatoRepository;
import lombok.*;
import org.springframework.stereotype.Service;

@Builder
@Setter
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Service
public class PlatoService {

    private final PlatoRepository platoRepository;

}