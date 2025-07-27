package com.grupo4.restaurante.services;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@Service
public class TitleService {

    // Mapa que asocia rutas (o claves únicas) con sus títulos dinámicos.
    private final Map<String, Map<String, String>> titulosPagina;

    public TitleService() {
        titulosPagina = new HashMap<>();

        // Títulos para la página de inicio.
        agregarTitulos("inicio", "Gestor de Restaurante | Inicio", "Bienvenido al Restaurante");

        // Página de contacto.
        agregarTitulos("contacto", "Gestor de Restaurante | Contacto", "Contactanos");

        // Cartas y Menús.
        agregarTitulos("menus/carne", "Gestor de Restaurante | Carta de Carnes", "Carta de Carnes");
        agregarTitulos("menus/pescado", "Gestor de Restaurante | Carta de Pescados", "Carta de Pescados");
        agregarTitulos("menus/vegetariano", "Gestor de Restaurante | Carta Vegetariana", "Carta Vegetariana");

        // Reservas
        agregarTitulos("reservas", "gestor de Restaurante | Reservas", "Reserva tu mesa");
    }

    private void agregarTitulos(String clave, String tituloPagina, String tituloCabecera) {
        Map<String, String> datos = new HashMap<>();
        datos.put("tituloPagina", tituloPagina);
        datos.put("tituloCabecera", tituloCabecera);
        titulosPagina.put(clave, datos);
    }

    public String getTituloPagina(String clave) {
        return titulosPagina.getOrDefault(clave, new HashMap<>()).getOrDefault("tituloPagina", "Gestor de Restaurante");
    }

    public String getTituloCabecera(String clave) {
        return titulosPagina.getOrDefault(clave, new HashMap<>()).getOrDefault("titulo<cabecera", "Gestor de Restaurante");
    }

}
