package com.grupo4.restaurante.controllers;

import com.grupo4.restaurante.dto.PaginaDTO;
import jakarta.servlet.http.HttpServletRequest;
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
    public String home(Model model, HttpServletRequest request) {
        PaginaDTO pagina = new PaginaDTO("Inicio | Restaurante");
        model.addAttribute("mensaje", "Bienvenido a Restaurante");
        model.addAttribute("uri", request.getRequestURI());
        return "home";  // Se refiere al archivo templates/home.html
    }


    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        model.addAttribute("pagina", new PaginaDTO("Iniciar sesión"));
        model.addAttribute("uri", request.getRequestURI());
        return "login";     // Se refiere a la vista login (sin extensión .html)
    }

    @GetMapping("/registro")
    public String registro(Model model, HttpServletRequest request) {
        model.addAttribute("pagina", new PaginaDTO("Registro de Usuario"));
        model.addAttribute("uri", request.getRequestURI());
        return "registro";      // Se refiere a la vista registro (sin extensión .html)
    }

    @GetMapping("/error")
    public String error(Model model, HttpServletRequest request ) {
        model.addAttribute("pagina", new PaginaDTO("Error"));
        model.addAttribute("uri", request.getRequestURI());
        return "error";     // Se refiere a la vista error para mostrar la página 404(sin extensión .html)
    }

    @GetMapping("/contacto")
    public String contacto(Model model, HttpServletRequest request) {
        model.addAttribute("pagina", new PaginaDTO("Contacto"));
        model.addAttribute("uri", request.getRequestURI());
        return "contacto";      // Se refiere a la vista contacto (sin extensión .html)
    }

    @GetMapping("/menu/vegano")
    public String menuVegano(Model model, HttpServletRequest request  ) {
        model.addAttribute("pagina", new PaginaDTO("Menú Vegano"));
        model.addAttribute("uri", request.getRequestURI());
        return "vegetariano";        // Se refiere a la vista categorias (sin extensión .html)
    }

    @GetMapping("//menu/carne")
    public String meuCarne(Model model, HttpServletRequest request  ) {
        model.addAttribute("pagina", new PaginaDTO("Menú de Carne"));
        model.addAttribute("uri", request.getRequestURI());
        return "carne";        // Se refiere a la vista categorias (sin extensión .html)
    }

    @GetMapping("/menu/pescado")
    public String menuPescado(Model model, HttpServletRequest request  ) {
        model.addAttribute("pagina", new PaginaDTO("Menú de Pescado"));
        model.addAttribute("uri", request.getRequestURI());
        return "pescado";        // Se refiere a la vista categorias (sin extensión .html)
    }

}
