package Sistema_GIS_La_Paz_Microservicios.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    @GetMapping("/calcular")
    public ResponseEntity<Map<String, Object>> calcularTarifa(
            @RequestParam String tipoVehiculo,
            @RequestParam String horario,
            @RequestParam String tramo,
            @RequestParam int tarifaDiferenciada) {
        
        // Validar parámetros de entrada
        if (!tipoVehiculo.equalsIgnoreCase("minibus")) {
            return ResponseEntity.badRequest().body(createErrorResponse("Tipo de vehículo no soportado. Solo 'minibus' disponible actualmente."));
        }
        
        if (!horario.equalsIgnoreCase("diurno")) {
            return ResponseEntity.badRequest().body(createErrorResponse("Horario no soportado. Solo 'diurno' disponible actualmente."));
        }
        
        if (!tramo.equalsIgnoreCase("corto") && !tramo.equalsIgnoreCase("largo")) {
            return ResponseEntity.badRequest().body(createErrorResponse("Tramo no válido. Use 'corto' o 'largo'."));
        }
        
        if (tarifaDiferenciada != 0 && tarifaDiferenciada != 1) {
            return ResponseEntity.badRequest().body(createErrorResponse("Tarifa diferenciada no válida. Use 0 (sin discapacidad) o 1 (con discapacidad)."));
        }
        
        // Calcular tarifa
        double tarifaBase = 0.0;
        
        if (tipoVehiculo.equalsIgnoreCase("minibus")) {
            if (tramo.equalsIgnoreCase("corto")) {
                tarifaBase = 2.40;
            } else { // largo
                tarifaBase = 3.00;
            }
        }
        
        // Aplicar descuento si tiene tarifa diferenciada (persona con discapacidad)
        double tarifaFinal = tarifaBase;
        boolean descuentoAplicado = false;
        
        if (tarifaDiferenciada == 1) {
            // 50% de descuento para personas con discapacidad
            tarifaFinal = tarifaBase * 0.5;
            descuentoAplicado = true;
        }
        
        // Preparar respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("tipoVehiculo", tipoVehiculo);
        response.put("horario", horario);
        response.put("tramo", tramo);
        response.put("tarifaBase", tarifaBase);
        response.put("descuentoAplicado", descuentoAplicado);
        response.put("tarifaFinal", tarifaFinal);
        
        return ResponseEntity.ok(response);
    }
    
    private Map<String, Object> createErrorResponse(String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("mensaje", mensaje);
        return response;
    }
}
