package es.decroly.tienda_decroly.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TiendaRestController {

    @GetMapping("/info")
    public String info() {
        return "Hola cliente <br>" +
                "Esta es la pagina de TiendaDecroly <br>" +
                "Situada en Santander, España <br>" +
                "Horario de lun a Vie:  9:00 AM a 10:00 PM <br>"
                 ;
    }

        @GetMapping("/descuento/{precio}")
    public String descuento(@PathVariable double precio, @RequestParam(defaultValue = "10") double descuento) {
        double precioFinal = precio - (precio * descuento / 100);
        return "El precio original es: " + precio + "<br> Y con el descuento de " + descuento + "<br>El precio final seria: " + precioFinal;
    }


}
