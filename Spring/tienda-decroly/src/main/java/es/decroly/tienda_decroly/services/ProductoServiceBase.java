package es.decroly.tienda_decroly.services;

import es.decroly.tienda_decroly.domain.Producto;
import es.decroly.tienda_decroly.exceptions.BadRequestException;
import es.decroly.tienda_decroly.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public abstract class ProductoServiceBase implements ProductoService{
    private final List<Producto> productos;
    private final AtomicLong secuencia;

    public ProductoServiceBase(List<Producto> prodInic) {
        this.productos = new ArrayList<>(prodInic);
        long maxId = 0;
        for (Producto prod : prodInic) {
            if (maxId < prod.getId()) {
                maxId = prod.getId();
            }
        }
        this.secuencia = new AtomicLong(maxId);
    }


    private void anadir(String nombre, double precio, int stock){
        long id = secuencia.incrementAndGet();
        productos.add(new Producto(id, nombre, precio, stock));

    }

    @Override
    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }

    @Override
    public Producto getProductosId(Long id) {
        Producto producto = buscarPorId(id);
        return producto;
    }

    @Override
    public Producto createProducto(Producto producto) {
        ValidarProducto(producto.getNombre(), producto.getPrecio(), producto.getStock());
        producto.setId(secuencia.incrementAndGet());
        productos.add(producto);
        log.info("Producto {} guardado correctamente", producto.getNombre());
        return producto;
    }

    @Override
    public Producto updateProducto(Long id, Producto producto) {
        Producto productoActual = buscarPorId(id);
        ValidarProducto(producto.getNombre(), producto.getPrecio(), producto.getStock());
        producto.setId(productoActual.getId());
        int indice = productos.indexOf(productoActual);
        productos.set(indice, producto);
        return productoActual;
    }

    @Override
    public void deleteProducto(Long id) {
        Producto productoActual = buscarPorId(id);
        productos.remove(productoActual);

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
