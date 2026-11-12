package es.decroly.gimnasio.service;

import es.decroly.gimnasio.config.GimnasioDecrolyPropeties;
import es.decroly.gimnasio.controller.GimnasioRestController;
import es.decroly.gimnasio.dto.ActividadPeticionDto;
import es.decroly.gimnasio.dto.GimnasioPeticionDto;
import es.decroly.gimnasio.exception.ConflictosException;
import es.decroly.gimnasio.exception.DatosNoValidosException;
import es.decroly.gimnasio.exception.RecursosNoEncontradosExeption;
import es.decroly.gimnasio.model.Actividad;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@Tag(name = "Actividaeds", description = "Operaciones relacionadas con la gestión de las actividades")
public class ActividadServiceImpl implements ActividadService{
   // private GimnasioDecrolyPropeties gimnasioDecrolyPropeties;

    private List<Actividad> actividades;
    private final AtomicLong sequence = new AtomicLong();

    public ActividadServiceImpl(GimnasioDecrolyPropeties gimnasioDecrolyPropeties) {
        // this.gimnasioDecrolyPropeties = gimnasioDecrolyPropeties;



        this.actividades = new ArrayList<>();

        actividades.add(new Actividad(sequence.incrementAndGet(),"Pilates", "Laura Gómez", 12, 0));
        actividades.add(new Actividad(sequence.incrementAndGet(),"Spinning", "Marco Ruiz", 20, 8));
        actividades.add(new Actividad(sequence.incrementAndGet(),"Yoga", "Laura Gómez", 15, 15));
        actividades.add(new Actividad(sequence.incrementAndGet(),"Body pump", "Ana Torres", 25, 3));
    }

    /*GimnasioPeticionDto info = new GimnasioPeticionDto(
            gimnasioDecrolyPropeties.nombre(),
            gimnasioDecrolyPropeties.maxPlazas());

     */


    @Override
    @Operation(summary = "Obtener lista de las actividades", description = "Permite obtener todas las actividades o filtrarlos opcionalmente por monitor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de las actividades obtenida con éxito",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Actividad.class))))
    })
    public List<Actividad> getActividades(String monitor) {
        if (monitor == null || monitor.isBlank()) {
            return new ArrayList<>(actividades);
        }
        return actividades.stream().filter(actividades ->actividades.getMonitor() != null
                && actividades.getMonitor().toLowerCase().contains(monitor.toLowerCase())).toList();
    }

    @Override
    @Operation(summary = "Obtener una actividad por ID", description = "Busca y devuelve una actividad específico basado en su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad encontrado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Actividad.class))),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrado", content = @Content),

    })
    public Actividad getActividadesById(Long id) {
        Actividad actividad = searchActividad(id);
        return actividad;
    }

    @Override
    @Operation(summary = "Crear una actividad nueva", description = "Valida y registra una nueva actividad en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Actividad creado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Actividad.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error de validación", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto al crear", content = @Content)
    })
    public Actividad createActividad(ActividadPeticionDto actividadDto) {
        validateActividad(actividadDto);
        Actividad actividad = new Actividad();
        actividad.setId(sequence.incrementAndGet());
        actividad.setNombre(actividadDto.nombre());
        actividad.setMonitor(actividadDto.monitor());
        actividad.setPlazas(actividadDto.plazas());
        actividad.setPlazasLibres(actividadDto.plazas());
        actividades.add(actividad);
        log.info("La actividad {} se ha guardado correctamente");
        return  actividad;
    }

    @Override
    @Operation(summary = "Actualizar una actividad existente", description = "Actualiza los datos de una actividad previamente registrada utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Actividad.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto al actualizar", content = @Content)
    })
    public Actividad updateActividad(Long id, ActividadPeticionDto actividadDto) {

        Actividad actividadExist = searchActividad(id);
        validateActividad(actividadDto);
        actividadExist.setNombre(actividadDto.nombre());
        actividadExist.setMonitor(actividadDto.monitor());
        actividadExist.setPlazas(actividadDto.plazas());
        if (actividadDto.plazas() < actividadExist.getPlazasLibres()) {
            throw new DatosNoValidosException("Las plazas disponibles no pueden ser menores a las plazas que existen");
        }
        actividades.add(actividadExist);
        return actividadExist;
    }

    @Override
    @Operation(summary = "Eliminar una actividad", description = "Elimina una actividad del sistema utilizando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad eliminada con éxito", content = @Content),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content)
    })
    public void deleteActividad(Long id) {
        Actividad actividad = searchActividad(id);
        actividades.remove(actividad);
    }

    @Override
    @Operation(summary = "Obtener actividades con plazas disponibles", description = "Permite obtener la lista de todas las actividades que cuentan con plazas disponibles para préstamo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Actividades con plazas disponibles obtenida con éxito",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Actividad.class))))
    })
    public List<Actividad> getActividadesDisponibles() {
        return actividades.stream().filter(actividad -> actividad.getPlazasLibres() > 0 ).toList();
    }


    public Actividad searchActividad(Long id) {
        for (Actividad actividad : actividades) {
            if (Objects.equals(actividad.getId(), id)) {
                return actividad;
            }
        }
        throw new RecursosNoEncontradosExeption("No se ah encontrado la actividad con la id: " + id);
    }

    public void validateActividad(ActividadPeticionDto actividadV) {

        //Valida que el nombre no sea nulo
        if (actividadV.nombre() == null || actividadV.nombre().isBlank()) {
            throw new DatosNoValidosException("El nombre de la actividad no puede estar vacio");
        }

        //Valida que el nombre no sea mayor a 50 caracteres
        if (actividadV.nombre().length() > 50) {
            throw new DatosNoValidosException("El nombre no puede ser mayor a 50 caracteres");
        }

        //Valida que el nombre no se repita
        boolean nameExists = actividades.stream()
                .anyMatch(actividad -> actividad.getNombre() != null && actividad.getNombre().equalsIgnoreCase(actividadV.nombre()));
        if (nameExists) {
            throw new ConflictosException("Ya existe una actividad con el nombre: " + actividadV.nombre());
        }

        //Valida que el monitor no sea nulo
        if (actividadV.monitor() == null || actividadV.monitor().isBlank()) {
            throw new DatosNoValidosException("El monitor de la actividad no puede estar vacio");
        }
        if (actividadV.plazas() < actividadV.plazas()) {
            throw new DatosNoValidosException("Las cantidad plazas no puede ser menores a las plazas libres");
        }
        //Valida que las plazas sean mayores a 0
        if  (actividadV.plazas() < 0) {
            throw new DatosNoValidosException("Las cantidad plazas no puede ser menores a 0");

        }

        //Valida que las plazas sean menores o iguales a las maximas de la configuracion
        /* if (actividadV.plazas() >= info.maxPlazas()){
            throw new DatosNoValidosException("Las plazas no pueden ser mayores al maximo del gimnasio: " + info.maxPlazas());
        }

         */

    }
}
