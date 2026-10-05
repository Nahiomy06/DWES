package es.decroly.tienda_decroly.controllers;

import es.decroly.tienda_decroly.domain.Producto;
import org.springframework.web.bind.annotation.*;

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
    public List<Producto> getProductos() {
        return productos;
    }

    @GetMapping("/{id}")
    public Producto getProductos(@PathVariable long id) {

        for (Producto p : productos) {
            if (Objects.equals(p.getId(), id)) {
                return p;
            }
        }
        return null;

//        return productos.stream()
//                .filter(p -> p.getId() == id)
//                .findFirst()
//                .orElse(null);
    }

    @PostMapping()
    public Producto createProducto(@RequestBody Producto producto) {
        producto.setId(secuencia.incrementAndGet());
        productos.add(producto);
        return producto;
    }


    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable long id, @RequestBody Producto producto) {

        for (int i = 0; i < productos.size(); i++) {
            if (Objects.equals(productos.get(i).getId(), id)) {
                producto.setId(id);
                productos.set(i, producto);
                return producto;
            } else {
                return null;
            }
        }

        return producto;
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable long id) {
        for (int i = 0; i < productos.size(); i++) {
            if (Objects.equals(productos.get(i).getId(), id)) {
                productos.remove(i);
                break;
            } else {
                return ;
            }
        }
    }


}
