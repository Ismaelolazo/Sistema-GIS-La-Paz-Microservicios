package main.java.Sistema_GIS_La_Paz_Microservicios.controller;

import Sistema_GIS_La_Paz_Microservicios.model.HistorialTarifaMicro;
import Sistema_GIS_La_Paz_Microservicios.model.TarifaMicro;
import Sistema_GIS_La_Paz_Microservicios.service.TarifaAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/micros/admin/tarifas")
public class TarifaAdminController {

    @Autowired
    private TarifaAdminService tarifaAdminService;

    // CREATE - Crear nueva tarifa
    @PostMapping
    public ResponseEntity<?> createTarifa(@RequestBody TarifaMicro nuevaTarifa,
                                         @RequestParam(required = false) Integer idUsuario) {
        try {
            // Si no se proporciona idUsuario, usar 0 como default
            Integer userId = idUsuario != null ? idUsuario : 0;
            
            // Asegurarse de que siempre sea Minibús
            nuevaTarifa.setTipoVehiculo("Minibús");
            
            // Verificar valores correctos para horario y tramo
            if (!esHorarioValido(nuevaTarifa.getHorario())) {
                return new ResponseEntity<>("Horario no válido. Debe ser 'Diurno' o 'Nocturno'", HttpStatus.BAD_REQUEST);
            }
            
            if (!esTramoValido(nuevaTarifa.getTramo())) {
                return new ResponseEntity<>("Tramo no válido. Debe ser 'Corto' o 'Largo'", HttpStatus.BAD_REQUEST);
            }
            
            if (!esCondicionUsuarioValida(nuevaTarifa.getCondicionUsuario())) {
                return new ResponseEntity<>("Condición de usuario no válida. Debe ser 'General', 'Tercera edad' o 'Discapacidad'", 
                                          HttpStatus.BAD_REQUEST);
            }
            
            TarifaMicro tarifaCreada = tarifaAdminService.createTarifa(nuevaTarifa, userId);
            return new ResponseEntity<>(tarifaCreada, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la tarifa: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - Obtener todas las tarifas de minibuses
    @GetMapping
    public ResponseEntity<List<TarifaMicro>> getAllTarifas() {
        List<TarifaMicro> tarifas = tarifaAdminService.getAllTarifas();
        return new ResponseEntity<>(tarifas, HttpStatus.OK);
    }

    // READ - Obtener tarifa por tramo, horario y condición de usuario
    @GetMapping("/buscar")
    public ResponseEntity<?> getTarifaByTramoHorarioAndCondicion(
            @RequestParam String tramo,
            @RequestParam String horario,
            @RequestParam(required = false, defaultValue = "General") String condicionUsuario) {
        
        if (!esHorarioValido(horario) || !esTramoValido(tramo) || !esCondicionUsuarioValida(condicionUsuario)) {
            return new ResponseEntity<>("Parámetros inválidos. Verificar horario, tramo y condición de usuario", 
                                      HttpStatus.BAD_REQUEST);
        }
        
        Optional<TarifaMicro> tarifa = tarifaAdminService.getTarifaByTramoHorarioAndCondicion(
                tramo, horario, condicionUsuario);
        
        if (tarifa.isPresent()) {
            return new ResponseEntity<>(tarifa.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró tarifa para el tramo: " + tramo + 
                                      ", horario: " + horario + 
                                      " y condición: " + condicionUsuario, HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE - Actualizar tarifa existente
    @PutMapping
    public ResponseEntity<?> updateTarifa(
            @RequestParam String tramo,
            @RequestParam String horario,
            @RequestParam(defaultValue = "General") String condicionUsuario,
            @RequestParam BigDecimal nuevoPrecio,
            @RequestParam(required = false) Integer idUsuario) {
        
        if (!esHorarioValido(horario) || !esTramoValido(tramo) || !esCondicionUsuarioValida(condicionUsuario)) {
            return new ResponseEntity<>("Parámetros inválidos. Verificar horario, tramo y condición de usuario", 
                                      HttpStatus.BAD_REQUEST);
        }
        
        try {
            // Si no se proporciona idUsuario, usar 0 como default
            Integer userId = idUsuario != null ? idUsuario : 0;
            TarifaMicro tarifaActualizada = tarifaAdminService.updateTarifa(
                    tramo, horario, condicionUsuario, nuevoPrecio, userId);
            
            return new ResponseEntity<>(tarifaActualizada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la tarifa: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Eliminar tarifa
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTarifa(
            @PathVariable Long id,
            @RequestParam(required = false) Integer idUsuario) {
        
        try {
            // Si no se proporciona idUsuario, usar 0 como default
            Integer userId = idUsuario != null ? idUsuario : 0;
            boolean deleted = tarifaAdminService.deleteTarifa(id, userId);
            
            if (deleted) {
                return new ResponseEntity<>("Tarifa eliminada exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se encontró tarifa con ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la tarifa: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener historial de tarifas
    @GetMapping("/historial")
    public ResponseEntity<List<HistorialTarifaMicro>> getHistorialTarifas(
            @RequestParam String tramo,
            @RequestParam String horario) {
        
        List<HistorialTarifaMicro> historial = tarifaAdminService.getHistorialTarifas(tramo, horario);
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }
    
    // Métodos para validar parámetros
    private boolean esHorarioValido(String horario) {
        return horario != null && (horario.equals("Diurno") || horario.equals("Nocturno"));
    }
    
    private boolean esTramoValido(String tramo) {
        return tramo != null && (tramo.equals("Corto") || tramo.equals("Largo"));
    }
    
    private boolean esCondicionUsuarioValida(String condicion) {
        return condicion != null && 
              (condicion.equals("General") || 
               condicion.equals("Tercera edad") || 
               condicion.equals("Discapacidad"));
    }
}