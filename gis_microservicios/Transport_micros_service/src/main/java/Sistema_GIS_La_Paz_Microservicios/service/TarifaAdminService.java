package main.java.Sistema_GIS_La_Paz_Microservicios.service;
import Sistema_GIS_La_Paz_Microservicios.model.HistorialTarifaMicro;
import Sistema_GIS_La_Paz_Microservicios.model.TarifaMicro;
import Sistema_GIS_La_Paz_Microservicios.repository.HistorialTarifaMicroRepository;
import Sistema_GIS_La_Paz_Microservicios.repository.TarifaMicroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TarifaAdminService {

    @Autowired
    private TarifaMicroRepository tarifaMicroRepository;

    @Autowired
    private HistorialTarifaMicroRepository historialTarifaMicroRepository;

    // Obtener todas las tarifas de micros/minibuses
    public List<TarifaMicro> getAllTarifas() {
        return tarifaMicroRepository.findByTipoVehiculo("Minibús");
    }

    // Obtener tarifa específica
    public Optional<TarifaMicro> getTarifaByTramoAndHorario(String tipoTramo, String tipoHorario) {
        return tarifaMicroRepository.findByHorarioAndTramoAndTipoVehiculo(tipoHorario, tipoTramo, "Minibús");
    }

    // Obtener tarifa específica incluyendo condición de usuario
    public Optional<TarifaMicro> getTarifaByTramoHorarioAndCondicion(String tipoTramo, String tipoHorario, String condicionUsuario) {
        return tarifaMicroRepository.findByHorarioAndTramoAndCondicionUsuario(tipoHorario, tipoTramo, condicionUsuario);
    }

    // Actualizar tarifa existente
    @Transactional
    public TarifaMicro updateTarifa(String tipoTramo, String tipoHorario, String condicionUsuario, BigDecimal nuevoPrecio, Integer idUsuario) {
        Optional<TarifaMicro> tarifaExistente = tarifaMicroRepository.findByHorarioAndTramoAndCondicionUsuario(
                tipoHorario, tipoTramo, condicionUsuario);
        
        if (!tarifaExistente.isPresent()) {
            throw new IllegalArgumentException("No existe tarifa para el tramo: " + tipoTramo + 
                                             ", horario: " + tipoHorario + 
                                             " y condición de usuario: " + condicionUsuario);
        }
        
        TarifaMicro tarifa = tarifaExistente.get();
        
        // Guardar en historial la tarifa anterior
        HistorialTarifaMicro historial = new HistorialTarifaMicro(
            tarifa.getTramo(),
            tarifa.getHorario(),
            tarifa.getPrecio(),
            LocalDateTime.now()
        );
        historial.setFechaFin(LocalDateTime.now());
        historial.setIdUsuario(idUsuario);
        historialTarifaMicroRepository.save(historial);
        
        // Actualizar tarifa
        tarifa.setPrecio(nuevoPrecio);
        
        return tarifaMicroRepository.save(tarifa);
    }

    // Crear nueva tarifa
    @Transactional
    public TarifaMicro createTarifa(TarifaMicro nuevaTarifa, Integer idUsuario) {
        // Asegurarnos que siempre sea para minibús
        nuevaTarifa.setTipoVehiculo("Minibús");
        
        // Verificar si ya existe una tarifa para ese tramo, horario y condición
        Optional<TarifaMicro> tarifaExistente = tarifaMicroRepository.findByHorarioAndTramoAndCondicionUsuario(
            nuevaTarifa.getHorario(), nuevaTarifa.getTramo(), nuevaTarifa.getCondicionUsuario());
        
        if (tarifaExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe una tarifa para el tramo: " + nuevaTarifa.getTramo() + 
                                             ", horario: " + nuevaTarifa.getHorario() + 
                                             " y condición de usuario: " + nuevaTarifa.getCondicionUsuario());
        }
        
        // Guardar tarifa
        TarifaMicro tarifaGuardada = tarifaMicroRepository.save(nuevaTarifa);
        
        // Registrar en historial
        HistorialTarifaMicro historial = new HistorialTarifaMicro(
            tarifaGuardada.getTramo(),
            tarifaGuardada.getHorario(),
            tarifaGuardada.getPrecio(),
            LocalDateTime.now()
        );
        historial.setIdUsuario(idUsuario);
        historialTarifaMicroRepository.save(historial);
        
        return tarifaGuardada;
    }

    // Eliminar tarifa
    @Transactional
    public boolean deleteTarifa(Long id, Integer idUsuario) {
        Optional<TarifaMicro> tarifaOpt = tarifaMicroRepository.findById(id);
        
        if (!tarifaOpt.isPresent()) {
            return false;
        }
        
        TarifaMicro tarifa = tarifaOpt.get();
        
        // Verificar que sea una tarifa de minibús
        if (!"Minibús".equals(tarifa.getTipoVehiculo())) {
            throw new IllegalArgumentException("Solo se pueden eliminar tarifas de minibús en este microservicio");
        }
        
        // Guardar en historial la tarifa eliminada
        HistorialTarifaMicro historial = new HistorialTarifaMicro(
            tarifa.getTramo(),
            tarifa.getHorario(),
            tarifa.getPrecio(),
            LocalDateTime.now()
        );
        historial.setFechaFin(LocalDateTime.now());
        historial.setIdUsuario(idUsuario);
        historialTarifaMicroRepository.save(historial);
        
        // Eliminar la tarifa
        tarifaMicroRepository.deleteById(id);
        
        return true;
    }

    // Obtener historial de tarifas
    public List<HistorialTarifaMicro> getHistorialTarifas(String tipoTramo, String tipoHorario) {
        return historialTarifaMicroRepository.findByTipoTramoAndTipoHorarioOrderByFechaInicioDesc(tipoTramo, tipoHorario);
    }
}