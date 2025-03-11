package Sistema_GIS_La_Paz_Microservicios.controller;

import Sistema_GIS_La_Paz_Microservicios.model.*;
import Sistema_GIS_La_Paz_Microservicios.model.TarifaRequest;
import Sistema_GIS_La_Paz_Microservicios.model.TarifaResponse;
import Sistema_GIS_La_Paz_Microservicios.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    @GetMapping("/calcular")
    public ResponseEntity<?> calcularTarifa(
            @RequestParam String tipoVehiculo,
            @RequestParam String horario,
            @RequestParam String tramo,
            @RequestParam Integer tarifaDiferenciada) {
        
        try {
            TarifaResponse response = tarifaService.calcularTarifa(tipoVehiculo, horario, tramo, tarifaDiferenciada);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al calcular la tarifa: " + e.getMessage());
        }
    }
    
    // Fix for POST endpoint that's using incompatible parameters
    @PostMapping("/calcular")
    public ResponseEntity<?> calcularTarifaPost(@RequestBody TarifaRequest request) {
        try {
            // Extract parameters from request
            boolean esDiurno = request.getTipoHorario() == TipoHorario.DIURNO;
            boolean esTramoCorto = request.getTipoTramo() == TipoTramo.CORTO;
            
            // Convert boolean tarifaDiferencial to Integer
            Integer tarifaDiferenciada = request.isTarifaDiferencial() ? 1 : 0;
            
            // Use the String-based method to maintain consistent behavior
            TarifaResponse response = tarifaService.calcularTarifa(
                request.getTipoVehiculo().name().toLowerCase(),
                esDiurno ? "diurno" : "nocturno",
                esTramoCorto ? "corto" : "largo",
                tarifaDiferenciada
            );
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al calcular la tarifa: " + e.getMessage());
        }
    }
}