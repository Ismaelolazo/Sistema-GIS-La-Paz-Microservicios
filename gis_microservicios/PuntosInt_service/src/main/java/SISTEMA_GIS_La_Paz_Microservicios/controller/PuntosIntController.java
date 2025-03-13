package SISTEMA_GIS_La_Paz_Microservicios.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SISTEMA_GIS_La_Paz_Microservicios.dto.PuntoInteresDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/puntos-interes")
public class PuntosIntController {
    
    private final List<PuntoInteresDTO> puntosInteres = new ArrayList<>();

    public PuntosIntController() {
        puntosInteres.add(new PuntoInteresDTO(1, "Plaza Murillo", "Plaza histórica en La Paz", -16.5000, -68.1500, "Histórico"));
        puntosInteres.add(new PuntoInteresDTO(2, "Teleférico Rojo", "Estación central del teleférico", -16.495, -68.133, "Transporte"));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PuntoInteresDTO>> listarPuntosInteres() {
        return ResponseEntity.ok(puntosInteres);
    }

    @GetMapping("/buscar")
    public ResponseEntity<PuntoInteresDTO> buscarPuntoPorNombre(@RequestParam String nombre) {
        return puntosInteres.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/agregar")
    public ResponseEntity<PuntoInteresDTO> agregarPuntoInteres(@Valid @RequestBody PuntoInteresDTO nuevoPunto) {
        nuevoPunto.setId(puntosInteres.size() + 1);
        puntosInteres.add(nuevoPunto);
        return ResponseEntity.ok(nuevoPunto);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PuntoInteresDTO> actualizarPuntoInteres(@PathVariable int id, @Valid @RequestBody PuntoInteresDTO datosActualizados) {
        for (PuntoInteresDTO punto : puntosInteres) {
            if (punto.getId() == id) {
                punto.setNombre(datosActualizados.getNombre());
                punto.setDescripcion(datosActualizados.getDescripcion());
                punto.setLatitud(datosActualizados.getLatitud());
                punto.setLongitud(datosActualizados.getLongitud());
                punto.setCategoria(datosActualizados.getCategoria());
                return ResponseEntity.ok(punto);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPuntoInteres(@PathVariable int id) {
        boolean eliminado = puntosInteres.removeIf(punto -> punto.getId() == id);
        if (eliminado) {
            return ResponseEntity.ok("Punto de interés eliminado exitosamente.");
        }
        return ResponseEntity.notFound().build();
    }
}
