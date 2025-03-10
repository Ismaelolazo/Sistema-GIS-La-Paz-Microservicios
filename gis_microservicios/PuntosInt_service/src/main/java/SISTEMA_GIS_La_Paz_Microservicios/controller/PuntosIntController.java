package SISTEMA_GIS_La_Paz_Microservicios.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
/**
 *
 * @author Marco
 */
@RestController
@RequestMapping("/api/puntos-interes")
public class PuntosIntController {

    private final List<Map<String, Object>> puntosInteres = new ArrayList<>();

    public PuntosIntController() {
        // Agregamos algunos datos de ejemplo
        puntosInteres.add(crearPuntoInteres(1, "Plaza Murillo", "Plaza histórica en La Paz", -16.5000, -68.1500, "Histórico"));
        puntosInteres.add(crearPuntoInteres(2, "Teleférico Rojo", "Estación central del teleférico", -16.495, -68.133, "Transporte"));
    }

    private Map<String, Object> crearPuntoInteres(int id, String nombre, String descripcion, double lat, double lon, String categoria) {
        Map<String, Object> punto = new HashMap<>();
        punto.put("id", id);
        punto.put("nombre", nombre);
        punto.put("descripcion", descripcion);
        punto.put("latitud", lat);
        punto.put("longitud", lon);
        punto.put("categoria", categoria);
        return punto;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Map<String, Object>>> listarPuntosInteres() {
        return ResponseEntity.ok(puntosInteres);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> buscarPuntoPorNombre(@RequestParam String nombre) {
        return puntosInteres.stream()
                .filter(p -> p.get("nombre").toString().equalsIgnoreCase(nombre))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/agregar")
    public ResponseEntity<Map<String, Object>> agregarPuntoInteres(@RequestBody Map<String, Object> nuevoPunto) {
        nuevoPunto.put("id", puntosInteres.size() + 1);
        puntosInteres.add(nuevoPunto);
        return ResponseEntity.ok(nuevoPunto);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizarPuntoInteres(@PathVariable int id, @RequestBody Map<String, Object> datosActualizados) {
        for (Map<String, Object> punto : puntosInteres) {
            if ((int) punto.get("id") == id) {
                punto.putAll(datosActualizados);
                return ResponseEntity.ok(punto);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPuntoInteres(@PathVariable int id) {
        boolean eliminado = puntosInteres.removeIf(punto -> (int) punto.get("id") == id);
        if (eliminado) {
            return ResponseEntity.ok("Punto de interés eliminado exitosamente.");
        }
        return ResponseEntity.notFound().build();
    }
}
