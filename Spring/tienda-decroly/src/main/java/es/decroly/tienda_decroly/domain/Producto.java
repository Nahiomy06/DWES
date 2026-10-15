package es.decroly.tienda_decroly.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private Long id;
    private String nombre;
    private double precio;
    private int stock;


    public double getPrecioIva() {
        return precio * 1.21;
    }
//
//    public double getPrecioTotal() {
//        return precio * stock;
//    }

}
