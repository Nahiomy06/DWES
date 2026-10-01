package es.decroly.tienda_decroly.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundoResController {

    @GetMapping("/saludo")
    public String saludo() {
        return "Hola Mundo, desde la tienda de decroly.";
    }

    @GetMapping("/saludo/{nombre}")
    public String saludo(@PathVariable String nombre) {
        return "Hola " + nombre + ", desde la tienda de decroly";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam(defaultValue = "todo") String texto) {
        return "Busca los productos que contengan: " + texto;
    }




}
