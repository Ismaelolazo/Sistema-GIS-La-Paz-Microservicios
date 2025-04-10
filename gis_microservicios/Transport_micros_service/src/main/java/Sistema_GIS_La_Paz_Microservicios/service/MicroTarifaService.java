package main.java.Sistema_GIS_La_Paz_Microservicios.service;
import Sistema_GIS_La_Paz_Microservicios.model.*;
import Sistema_GIS_La_Paz_Microservicios.repository.TarifaMicroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class MicroTarifaService {

    @Autowired
    private TarifaMicroRepository tarifaMicroRepository;

    public TarifaResponse calcularTarifa(String horario, String tramo, Integer tarifaDiferenciada) {
        BigDecimal tarifaBase;
        boolean descuentoAplicado = false;
        BigDecimal tarifaFinal;

        // Validar horario
        horario = normalizarHorario(horario);
        if (horario == null) {
            throw new IllegalArgumentException("Horario no válido. Los valores permitidos son: diurno, nocturno");
        }

        // Validar tramo
        tramo = normalizarTramo(tramo);
        if (tramo == null) {
            throw new IllegalArgumentException("Tramo no válido. Los valores permitidos son: corto, largo");
        }

        // Determinar tipo de condición de usuario según si aplica tarifa diferenciada
        String condicionUsuario = "General";
        if (tarifaDiferenciada == 1) {
            condicionUsuario = "Tercera edad"; // Podría ser también "Discapacidad", pero usamos tercera edad por defecto
            descuentoAplicado = true;
        }

        // Buscar tarifa en la base de datos para la condición específica
        Optional<TarifaMicro> tarifaOpt = tarifaMicroRepository.findByHorarioAndTramoAndCondicionUsuario(
                horario, tramo, condicionUsuario);

        if (tarifaOpt.isPresent()) {
            // Si existe una tarifa específica para esa condición, la usamos
            tarifaBase = tarifaOpt.get().getPrecio();
            tarifaFinal = tarifaBase;
        } else {
            // Si no hay tarifa específica, buscamos la tarifa general
            Optional<TarifaMicro> tarifaGeneralOpt = tarifaMicroRepository.findByHorarioAndTramoAndCondicionUsuario(
                    horario, tramo, "General");
            
            if (tarifaGeneralOpt.isPresent()) {
                tarifaBase = tarifaGeneralOpt.get().getPrecio();
                
                // Si es tarifa diferenciada y no hay una específica, aplicamos 50% descuento a la general
                if (descuentoAplicado) {
                    tarifaFinal = tarifaBase.multiply(new BigDecimal("0.5")).setScale(2, RoundingMode.HALF_UP);
                } else {
                    tarifaFinal = tarifaBase;
                }
            } else {
                // Si no hay ninguna tarifa, usamos valores predeterminados
                if (tramo.equalsIgnoreCase("Corto")) {
                    tarifaBase = new BigDecimal("2.60");
                } else {
                    tarifaBase = new BigDecimal("2.80");
                }

                // Aplicar ajustes según horario
                if (horario.equalsIgnoreCase("Nocturno")) {
                    tarifaBase = tarifaBase.multiply(new BigDecimal("1.1")).setScale(2, RoundingMode.HALF_UP);
                }

                // Aplicar descuento si corresponde
                if (descuentoAplicado) {
                    tarifaFinal = tarifaBase.multiply(new BigDecimal("0.5")).setScale(2, RoundingMode.HALF_UP);
                } else {
                    tarifaFinal = tarifaBase;
                }
            }
        }

        return new TarifaResponse(horario, tramo, tarifaBase, descuentoAplicado, tarifaFinal);
    }
    
    // Métodos auxiliares para normalizar valores según la BD
    private String normalizarHorario(String horario) {
        if (horario == null) return null;
        
        horario = horario.toLowerCase();
        if (horario.equals("diurno")) {
            return "Diurno";
        } else if (horario.equals("nocturno")) {
            return "Nocturno";
        } else {
            return null;
        }
    }
    
    private String normalizarTramo(String tramo) {
        if (tramo == null) return null;
        
        tramo = tramo.toLowerCase();
        if (tramo.equals("corto")) {
            return "Corto";
        } else if (tramo.equals("largo")) {
            return "Largo";
        } else {
            return null;
        }
    }
}