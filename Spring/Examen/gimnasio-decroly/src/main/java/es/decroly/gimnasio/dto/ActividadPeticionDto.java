package es.decroly.gimnasio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(name = "ActividadPeticionDto", description = "Datos de petición para la creación o actualización de una actividad")
public record ActividadPeticionDto (
        @Schema(description = "El nombre de la actividad", example = "Pilates", requiredMode = Schema.RequiredMode.REQUIRED)
        String nombre,
        @Schema(description = "Nombre del monitor de la actividad", example = "Maria Perez", requiredMode = Schema.RequiredMode.REQUIRED)
        String monitor,
        @Schema(description = "Número de plazas totales para la actividad", example = "15", requiredMode = Schema.RequiredMode.REQUIRED)
        int plazas
){

}
