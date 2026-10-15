package es.decroly.tienda_decroly.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Api de la tienda decroly",
        version = "1.0",
        description = "Catalogos de productos de la tienda"))
public class OpenApiConfig {

}
