package com.grupo4.restaurante.controllers;


import com.grupo4.restaurante.entities.Producto;
import com.grupo4.restaurante.repositories.CategoriaRepository;
import com.grupo4.restaurante.repositories.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//Controlador de productos
@Controller
@RequestMapping("/producto")
public class ProductoController {

    //
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoController(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String mostrarListaProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "producto-list.html";
    }

    @GetMapping("/nuevo")
    public String crearProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "producto-formulario.html";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoRepository.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        Producto producto = productoRepository.finById(Id).orElseThrow(() -> new IllegalArgumentException("ID invalido: " + id));
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "producto-formulario.html";
    }

    @GetMapping("/detalle/{id}")
    public String mostrarDetalleProducto(@pathVariable Long id, Model model) {
        Producto producto = productoRepository.findById(id).orElseThrow(null);
        model.addAttribute("producto", producto);
        return "producto-detalle.html";
    }
}
