package es.decroly.gimnasio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gimnasio")
public record GimnasioDecrolyPropeties(
        String nombre,
        int maxPlazas
){

}
