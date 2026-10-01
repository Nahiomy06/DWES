package es.decroly.tienda_decroly.controllers;

import es.decroly.tienda_decroly.domain.Producto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ProductoRestController {
    //Provicional: Los datos van a estar temporalmente en  memoria
    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong();
    public ProductoRestController() {
        anadir("Manzana", 1.5, 25);
        anadir("Pera", 1.5, 25);
        anadir("Banana", 1.5, 25);

    }

    private void anadir(String nombre, double precio, int stock){
        long id = secuencia.incrementAndGet();
        productos.add(new Producto(id, nombre, precio, stock));

    }

    @GetMapping("/api/Productos")
    public List<Producto> getProductos() {
        return productos;
    }

    @GetMapping("/api/Productos/{id}")
    public Producto getProductos(@PathVariable long id) {
        return productos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

}
