package es.decroly.gimnasio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Schema(name = "GimnasioPeticionDto", description = "DTO que representa la información y configuración general del gimnasio")
public record GimnasioPeticionDto (
        @Schema(description = "Nombre oficial del gimnasio", example = "Gimnasio  Decroly", requiredMode = Schema.RequiredMode.REQUIRED)
        String nombre,
        @Schema(description = "Número de plazas maximas para una actividad", example = "30", requiredMode = Schema.RequiredMode.REQUIRED)
        int maxPlazas){
}
