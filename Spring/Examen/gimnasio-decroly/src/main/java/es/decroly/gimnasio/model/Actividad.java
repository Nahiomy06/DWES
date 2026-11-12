package es.decroly.gimnasio.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Actividad", description = "Entidad que representa una actividad en el sistema del gimnasio")
public class Actividad {
    @Schema(description = "Identificador único de la actividad", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nombre de la actividad", example = "Pilates", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(description = "Nombre del monitor de la actividad", example = "Laura", requiredMode = Schema.RequiredMode.REQUIRED)
    private String monitor;

    @Schema(description = "Número de plazas totales para la actividad", example = "15", requiredMode = Schema.RequiredMode.REQUIRED)
    private int plazas;

    @Schema(description = "Número de plazas que se encuentran libres para la actividad", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private int  plazasLibres;


}
