package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.PaginaDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Controlador principal que gestiona las rutas del sitio web del restaurante.
 * Cada mét-odo está vinculado a una vista Thymeleaf ubicada en la carpeta "templates".
 * Función que responde a las peticiones GET an la raíz del sitio ("/").
 * Devuelve la vista "home", ubicada en la carpeta templates.
 *
 * Author: David De La Puente Enriquez y Luis Miguel Haro.
 * @return Nombre de la vista "home" (sin extensión .html)
 * */
@Controller
public class HomeController {

    /**
     *  Mapea la URL raíz ("/") a la vista home.html
     *
     * @param model Objeto para enviar datos a la vista.
     * @param request Petición HTTP con la URI solicitada.
     * @return Nombre de la plantilla "home" (sin extensión .html).
      */

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("tituloPagina", "Restaurante ERP");
        model.addAttribute("tituloCabecera", ("Inicio | Restaurante"));
        model.addAttribute("tituloContenido", "Bienvenido a Su Gestor de Restaurante V.1.0.0");
        model.addAttribute("uri", request.getRequestURI());
        model.addAttribute("footerType", "footer-default");
        return "home";  // Se refiere al archivo templates/home.html
    }

    /**
     * Página de error 404 o acceso denegado.
     */
    @GetMapping("/error")
    public String error(Model model, HttpServletRequest request ) {
        model.addAttribute("pagina", new PaginaDTO("Error de la Aplicación"));
        model.addAttribute("tituloPagina", "Error | Restaurante ERP");
        model.addAttribute("tituloCabecera", "Ha ocurrido un Error");
        model.addAttribute("uri", request.getRequestURI());
        return "error";     // Se refiere a la vista error para mostrar la página 404(sin extensión .html)
    }
}
