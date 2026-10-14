package es.decroly.tienda_decroly.controllers;

import es.decroly.tienda_decroly.domain.Producto;
import es.decroly.tienda_decroly.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/Productos")
public class ProductoRestController {
    //Provicional: Los datos van a estar temporalmente en  memoria
    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong();
    private final ProductoService productoService;

    public ProductoRestController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping()
    public List<Producto> getProductos() {
        return productoService.getProductos();
    }

    @GetMapping("/{id}")
    public Producto getProductosId(@PathVariable long id) {
        return productoService.getProductosId(id);
    }

    @PostMapping()
    public Producto createProducto(@RequestBody Producto producto) {

        return productoService.createProducto(producto);

    }


    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable long id, @RequestBody Producto producto) {
        return productoService.updateProducto(id, producto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProducto(@PathVariable long id) {
        productoService.deleteProducto(id);
    }


    }
