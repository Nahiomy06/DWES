package es.decroly.gimnasio.controller;

import es.decroly.gimnasio.dto.ActividadPeticionDto;
import es.decroly.gimnasio.model.Actividad;
import es.decroly.gimnasio.service.ActividadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/actividades")
@Tag(name = "Actividades", description = "Gestión integral del catálogo de las actividades: operaciones para la consulta, creación, actualización y eliminación de las actividades.")
public class ActividadesRestController {

    private final ActividadService actividadService;

    @GetMapping
    @Operation(summary = "Crear una actividad nueva", description = "Valida y registra una nueva actividad en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Actividad creado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Actividad.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error de validación", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto al actualizar", content = @Content)
    })
    public List<Actividad> getActividades(@RequestParam(required = false) String monitor){
        return actividadService.getActividades(monitor);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una actividad por ID", description = "Busca y devuelve una actividad específico basado en su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad encontrado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Actividad.class))),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrado", content = @Content),

    })
    public Actividad getActividadById(@PathVariable Long id){
        return actividadService.getActividadesById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una actividad nueva", description = "Valida y registra una nueva actividad en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Actividad creado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Actividad.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error de validación", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto al crear", content = @Content)
    })
    public Actividad createActividad(@RequestBody ActividadPeticionDto actividad){
        return actividadService.createActividad(actividad);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una actividad existente", description = "Actualiza los datos de una actividad previamente registrada utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Actividad.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto al actualizar", content = @Content)

    })
    public Actividad updateActividad(@PathVariable Long id, @RequestBody ActividadPeticionDto actividad){
        return actividadService.updateActividad(id, actividad);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar una actividad", description = "Elimina una actividad del sistema utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad eliminada con éxito", content = @Content),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content)
    })
    public void deleteActividad(@PathVariable Long id){
        actividadService.deleteActividad(id);
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Obtener actividades con plazas disponibles", description = "Permite obtener la lista de todas las actividades que cuentan con plazas disponibles para préstamo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Actividades con plazas disponibles obtenida con éxito",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Actividad.class))))
    })
    public List<Actividad> getActividadesDisponibles(){
        return actividadService.getActividadesDisponibles();
    }



}
