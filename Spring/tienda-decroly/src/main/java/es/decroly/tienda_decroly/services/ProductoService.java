package es.decroly.tienda_decroly.services;

import es.decroly.tienda_decroly.domain.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> getProductos();
    Producto getProductosId(Long id);
    Producto createProducto(Producto producto);
    Producto updateProducto(Long id, Producto datos);
    void deleteProducto(Long id);

}
