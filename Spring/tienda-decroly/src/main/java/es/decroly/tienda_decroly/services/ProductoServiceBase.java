package es.decroly.tienda_decroly.services;

import es.decroly.tienda_decroly.domain.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


public class ProductoServiceBase {
    private final List<Producto> productos;
    private final AtomicLong secuencia;

    public ProductoServiceBase(List<Producto> prodInic) {
        this.productos = new ArrayList<>();
        long maxId = 0;
        for (Producto prod : prodInic) {
            if (maxId < prod.getId()) {
                maxId = prod.getId();
            }
        }
        this.secuencia = new AtomicLong(maxId);
    }



}
