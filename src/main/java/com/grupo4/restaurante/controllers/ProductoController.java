package com.grupo4.restaurante.controllers;


import com.grupo4.restaurante.entities.Producto;
import com.grupo4.restaurante.repositories.CategoriaRepository;
import com.grupo4.restaurante.repositories.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//Controlador de productos
@Controller
@RequestMapping("/productos")
public class ProductoController {

    // Repositorios de la aplicación.
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoController(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Métodos para gestionar las peticiones HTTP de la aplicación.
    @GetMapping
    public String mostrarListaProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "productos-list.html";
    }

    @GetMapping("/nuevo")
    public String crearProducto(Model model) {
        model.addAttribute("productos", new Producto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "productos-formulario.html";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoRepository.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID invalido: " + id));
        model.addAttribute("productos", producto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "productos-formulario.html";
    }

    @GetMapping("/detalle/{id}")
    public String mostrarDetalleProducto(@PathVariable Long id, Model model) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID invalido: " + id));
        model.addAttribute("productos", producto);
        return "productos-detalle.html";
    }
}
