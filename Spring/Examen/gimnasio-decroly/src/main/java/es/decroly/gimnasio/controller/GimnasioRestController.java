package es.decroly.gimnasio.controller;

import es.decroly.gimnasio.config.GimnasioDecrolyPropeties;
import es.decroly.gimnasio.dto.GimnasioPeticionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gimnasio")
@Tag(name = "Información del gimnasio", description = "Endpoints para consultar la configuración e información general del gimnasio")
public class GimnasioRestController {
    private final GimnasioDecrolyPropeties gimnasioDecrolyPropeties;

    public GimnasioRestController(GimnasioDecrolyPropeties gimnasioDecrolyPropeties) {
        this.gimnasioDecrolyPropeties = gimnasioDecrolyPropeties;
    }


    @GetMapping("/info")
    @Operation(summary = "Obtener información general del gimnasio", description = "Devuelve los datos de configuración generales como el nombre y las maximas plazas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información obtenida con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GimnasioPeticionDto.class)))
    })
    public GimnasioPeticionDto getInfo(){
        return new GimnasioPeticionDto(
            gimnasioDecrolyPropeties.nombre(),
            gimnasioDecrolyPropeties.maxPlazas()
        );
    }



}
