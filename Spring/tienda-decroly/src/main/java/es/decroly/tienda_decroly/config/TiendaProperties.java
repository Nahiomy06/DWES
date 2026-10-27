package es.decroly.tienda_decroly.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tienda")
public record TiendaProperties(String nombre, double iva, double envioGratisDesde) {



}
