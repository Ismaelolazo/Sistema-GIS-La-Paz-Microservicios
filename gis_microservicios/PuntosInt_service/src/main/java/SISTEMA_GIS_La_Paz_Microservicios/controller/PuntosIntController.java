package SISTEMA_GIS_La_Paz_Microservicios.controller;

import SISTEMA_GIS_La_Paz_Microservicios.dto.PuntoInteresDTO;
import SISTEMA_GIS_La_Paz_Microservicios.service.PuntoInteresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/puntos-interes")
@Tag(name = "Puntos de Interés", description = "Operaciones para la gestión de puntos de interés")
public class PuntosIntController {

    @Autowired
    private PuntoInteresService puntoInteresService;

    @GetMapping("/listar")
    @Operation(summary = "Listar todos los puntos de interés", description = "Retorna una lista de todos los puntos de interés disponibles.")
    public ResponseEntity<List<PuntoInteresDTO>> listarPuntosInteres() {
        return ResponseEntity.ok(puntoInteresService.listarPuntosInteres());
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar un punto de interés por nombre", description = "Busca un punto de interés utilizando su nombre.")
    public ResponseEntity<?> buscarPuntoPorNombre(@RequestParam String nombre) {
        Optional<PuntoInteresDTO> punto = puntoInteresService.buscarPorNombre(nombre);
        return punto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/agregar")
    @Operation(summary = "Agregar un nuevo punto de interés", description = "Crea un nuevo punto de interés en el sistema.")
    public ResponseEntity<?> agregarPuntoInteres(@RequestBody PuntoInteresDTO nuevoPunto) {
        try {
            PuntoInteresDTO puntoCreado = puntoInteresService.agregarPuntoInteres(nuevoPunto);
            return ResponseEntity.ok(puntoCreado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al agregar el punto de interés: " + e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualizar un punto de interés", description = "Modifica la información de un punto de interés existente.")
    public ResponseEntity<?> actualizarPuntoInteres(@PathVariable int id, @RequestBody PuntoInteresDTO datosActualizados) {
        Optional<PuntoInteresDTO> puntoActualizado = puntoInteresService.actualizarPuntoInteres(id, datosActualizados);
        return puntoActualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar un punto de interés", description = "Elimina un punto de interés por su ID.")
    public ResponseEntity<String> eliminarPuntoInteres(@PathVariable int id) {
        if (puntoInteresService.eliminarPuntoInteres(id)) {
            return ResponseEntity.ok("Punto de interés eliminado exitosamente.");
        }
        return ResponseEntity.notFound().build();
    }
}
