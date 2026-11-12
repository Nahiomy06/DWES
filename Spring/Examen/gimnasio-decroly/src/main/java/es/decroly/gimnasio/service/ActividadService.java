package es.decroly.gimnasio.service;

import es.decroly.gimnasio.dto.ActividadPeticionDto;
import es.decroly.gimnasio.model.Actividad;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface ActividadService {
    @Operation(summary = "Obtener lista de las actividades", description = "Permite obtener todas las actividades o filtrarlos opcionalmente por monitor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de las actividades obtenida con éxito",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Actividad.class))))
    })
    List<Actividad> getActividades(String monitor);

    @Operation(summary = "Obtener una actividad por ID", description = "Busca y devuelve una actividad específico basado en su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad encontrado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Actividad.class))),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrado", content = @Content),

    })
    Actividad getActividadesById(Long id);

    @Operation(summary = "Crear una actividad nueva", description = "Valida y registra una nueva actividad en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Actividad creado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Actividad.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error de validación", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto al crear", content = @Content)
    })
    Actividad createActividad(ActividadPeticionDto actividad);

    @Operation(summary = "Actualizar una actividad existente", description = "Actualiza los datos de una actividad previamente registrada utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Actividad.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto al actualizar", content = @Content)
    })
    Actividad updateActividad(Long id, ActividadPeticionDto actividad);

    @Operation(summary = "Eliminar una actividad", description = "Elimina una actividad del sistema utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad eliminada con éxito", content = @Content),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content)
    })
    void deleteActividad(Long id);

    @Operation(summary = "Obtener actividades con plazas disponibles", description = "Permite obtener la lista de todas las actividades que cuentan con plazas disponibles para préstamo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Actividades con plazas disponibles obtenida con éxito",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Actividad.class))))
    })
    List<Actividad> getActividadesDisponibles();

}
