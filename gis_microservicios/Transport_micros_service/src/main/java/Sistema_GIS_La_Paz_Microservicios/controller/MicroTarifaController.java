package main.java.Sistema_GIS_La_Paz_Microservicios.controller;

import Sistema_GIS_La_Paz_Microservicios.model.*;
import Sistema_GIS_La_Paz_Microservicios.service.MicroTarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/micros/tarifas")
public class MicroTarifaController {

    @Autowired
    private MicroTarifaService microTarifaService;

    @GetMapping("/calcular")
    public ResponseEntity<?> calcularTarifa(
            @RequestParam String horario,
            @RequestParam String tramo,
            @RequestParam Integer tarifaDiferenciada) {
        
        try {
            TarifaResponse response = microTarifaService.calcularTarifa(horario, tramo, tarifaDiferenciada);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al calcular la tarifa: " + e.getMessage());
        }
    }
    
    @PostMapping("/calcular")
    public ResponseEntity<?> calcularTarifaPost(@RequestBody TarifaRequest request) {
        try {
            // Extract parameters from request
            boolean esDiurno = request.getTipoHorario() == TipoHorario.DIURNO;
            boolean esTramoCorto = request.getTipoTramo() == TipoTramo.CORTO;
            
            // Convert boolean tarifaDiferencial to Integer
            Integer tarifaDiferenciada = request.isTarifaDiferencial() ? 1 : 0;
            
            // Use the String-based method
            TarifaResponse response = microTarifaService.calcularTarifa(
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