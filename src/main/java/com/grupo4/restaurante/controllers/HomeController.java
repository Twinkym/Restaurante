package com.grupo4.restaurante.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    /**
     * Función que responde a las peticiones GET an la raís del sitio ("/").
     * Devuelve la vista "home", ubicada en la carpeta templates.
     *
     * @return  el nombre de la vista (sin extensión .html)
     * */
    // Mapea la URL raíz ("/") a la vista home.html
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titulo", "Inicio | Restaurante");
        model.addAttribute("mensaje", "Bienvenido a Restaurante");
        return "home";  // Se refiere al archivo templates/home.html
    }

    @GetMapping("/login")
    public String login() {
        return "login";     // Se refiere a la vista login (sin extensión .html)
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";      // Se refiere a la vista registro (sin extensión .html)
    }

    @GetMapping("/error")
    public String error() {
        return "error";     // Se refiere a la vista error para mostrar la pagina 404(sin extensión .html)
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";      // Se refiere a la vista contacto (sin extensión .html)
    }

    @GetMapping("/productos")
    public String productos() {
        return "productos";     // Se refiere a la vista productos (sin extensión .html)
    }

    @GetMapping("/categorias")
    public String categorias() {
        return "categorias";        // Se refiere a la vista categorias (sin extensión .html)
    }
}
