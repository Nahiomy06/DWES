package es.decroly.tienda_decroly.controllers;

import es.decroly.tienda_decroly.domain.Producto;
import es.decroly.tienda_decroly.exceptions.BadRequestException;
import es.decroly.tienda_decroly.exceptions.NotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/Productos")
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

    @GetMapping()
    public ResponseEntity<List<Producto>> getProductos() {
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public Producto getProductosId(@PathVariable long id) {
        Producto producto = buscarPorId(id);
        return producto;

//        for (Producto p : productos) {
//            if (Objects.equals(p.getId(), id)) {
//                return ResponseEntity.ok(p);
//            }
//        }
//        throw  new NotFoundException("No existe el producto con la id " + id);

//        return productos.stream()
//                .filter(p -> p.getId() == id)
//                .findFirst()
//                .orElse(null);
    }

    @PostMapping()
    public Producto createProducto(@RequestBody Producto producto) {

        ValidarProducto(producto.getNombre(), producto.getPrecio(), producto.getStock());
        producto.setId(secuencia.incrementAndGet());
        productos.add(producto);
        return producto;


//        producto.setId(secuencia.incrementAndGet());
//        productos.add(producto);
//        String URI = String.format("/api/productos/%d", producto.getId());
//        return producto;
    }


    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable long id, @RequestBody Producto producto) {

        Producto productoActual = buscarPorId(id);
        ValidarProducto(producto.getNombre(), producto.getPrecio(), producto.getStock());
        producto.setId(productoActual.getId());
        int indice = productos.indexOf(productoActual);
        productos.set(indice, producto);
        return productoActual;
//        for (int i = 0; i < productos.size(); i++) {
//            Producto p = productos.get(i);
//            if (Objects.equals(p.getId(), id)) {
//                producto.setId(id);
//                productos.set(i, producto);
//                return ResponseEntity.ok(producto);
//            }
//        }
//        throw  new NotFoundException("No existe el producto con la id " + id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProducto(@PathVariable long id) {

        Producto productoActual = buscarPorId(id);
        productos.remove(productoActual);

//        for (int i = 0; i < productos.size(); i++) {
//            if (Objects.equals(productos.get(i).getId(), id)) {
//                productos.remove(i);
//                return ResponseEntity.noContent().build(); //204
//            }
//        }
//        throw  new NotFoundException("No existe el producto con la id " + id);
    }


    private Producto buscarPorId(long id) {
        for (Producto p : productos) {
            if (Objects.equals(p.getId(), id)) {
                return p;
            }
        }
        throw  new NotFoundException("No existe el producto con la id " + id);
    }

    private void ValidarProducto(String nombre, double precio, int stock) {

        if (nombre == null || nombre.isBlank()) {
            throw new BadRequestException("El nombre no puede estar vacio");
        }

        if (precio <= 0) {
            throw new BadRequestException("El precio no puede ser negativo");
        }

        if (stock < 0) {
            throw new BadRequestException("El stock no puede ser negativo");
        }
    }


    }
