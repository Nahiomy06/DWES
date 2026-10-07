package es.decroly.tienda_decroly.controllers;

import es.decroly.tienda_decroly.domain.Producto;
import org.apache.coyote.Response;
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
    public ResponseEntity<Producto> getProductosId(@PathVariable long id) {

        for (Producto p : productos) {
            if (Objects.equals(p.getId(), id)) {
                return ResponseEntity.ok(p);
            }
        }
        return  ResponseEntity.notFound().build(); //Error 4040

//        return productos.stream()
//                .filter(p -> p.getId() == id)
//                .findFirst()
//                .orElse(null);
    }

    @PostMapping()
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        producto.setId(secuencia.incrementAndGet());
        productos.add(producto);
        String URI = String.format("/api/productos/%d", producto.getId());
        return ResponseEntity.created(java.net.URI.create(URI)).body(producto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable long id, @RequestBody Producto producto) {

        for (int i = 0; i < productos.size(); i++) {
            if (Objects.equals(productos.get(i).getId(), id)) {
                producto.setId(id);
                productos.set(i, producto);
                return ResponseEntity.ok(producto);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable long id) {
        for (int i = 0; i < productos.size(); i++) {
            if (Objects.equals(productos.get(i).getId(), id)) {
                productos.remove(i);
                return ResponseEntity.noContent().build(); //204
            }
        }
        return ResponseEntity.notFound().build(); //404
    }


}
