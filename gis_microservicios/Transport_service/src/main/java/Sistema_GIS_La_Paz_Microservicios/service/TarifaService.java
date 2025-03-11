package Sistema_GIS_La_Paz_Microservicios.service;

import Sistema_GIS_La_Paz_Microservicios.model.*;
import org.springframework.stereotype.Service;

@Service
public class TarifaService {

    public TarifaResponse calcularTarifa(String tipoVehiculo, String horario, String tramo, Integer tarifaDiferenciada) {
        double tarifaBase = 0;
        boolean descuentoAplicado = false;
        double tarifaFinal;

        // Validar tipo de vehículo
        tipoVehiculo = tipoVehiculo.toLowerCase();
        if (!tipoVehiculo.equals("minibus") && !tipoVehiculo.equals("trufi") && 
            !tipoVehiculo.equals("micro") && !tipoVehiculo.equals("teleferico")) {
            throw new IllegalArgumentException("Tipo de vehículo no válido. Los valores permitidos son: minibus, trufi, micro, teleferico");
        }

        // Validar horario
        horario = horario.toLowerCase();
        if (!horario.equals("diurno") && !horario.equals("nocturno")) {
            throw new IllegalArgumentException("Horario no válido. Los valores permitidos son: diurno, nocturno");
        }

        // Validar tramo
        tramo = tramo.toLowerCase();
        if (!tramo.equals("corto") && !tramo.equals("largo")) {
            throw new IllegalArgumentException("Tramo no válido. Los valores permitidos son: corto, largo");
        }

        // Calcular tarifa base según tipo de vehículo y tramo
        switch (tipoVehiculo) {
            case "minibus":
                tarifaBase = tramo.equals("corto") ? 2.40 : 3.00;
                break;
            case "trufi":
                tarifaBase = tramo.equals("corto") ? 2.00 : 2.50;
                break;
            case "micro":
                tarifaBase = tramo.equals("corto") ? 2.60 : 2.80;
                break;
            case "teleferico":
                tarifaBase = tramo.equals("corto") ? 3.00 : 5.00;
                break;
        }

        // Aplicar ajustes según horario (por ejemplo, tarifa nocturna puede tener un incremento)
        if (horario.equals("nocturno")) {
            tarifaBase *= 1.1; // 10% adicional en horario nocturno
        }

        // Aplicar tarifa diferenciada si corresponde (1 = aplicar descuento, 0 = sin descuento)
        if (tarifaDiferenciada == 1) {
            tarifaFinal = tarifaBase * 0.5; // 50% de descuento
            descuentoAplicado = true;
        } else {
            tarifaFinal = tarifaBase;
        }

        return new TarifaResponse(tipoVehiculo, horario, tramo, tarifaBase, descuentoAplicado, tarifaFinal);
    }
}