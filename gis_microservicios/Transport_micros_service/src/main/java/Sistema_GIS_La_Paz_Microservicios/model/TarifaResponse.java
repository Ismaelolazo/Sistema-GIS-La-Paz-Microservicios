package main.java.Sistema_GIS_La_Paz_Microservicios.model;
import java.math.BigDecimal;

public class TarifaResponse {
    private String horario;
    private String tramo;
    private BigDecimal tarifaBase;
    private boolean descuentoAplicado;
    private BigDecimal tarifaFinal;

    public TarifaResponse() {
    }

    public TarifaResponse(String horario, String tramo, BigDecimal tarifaBase, boolean descuentoAplicado, BigDecimal tarifaFinal) {
        this.horario = horario;
        this.tramo = tramo;
        this.tarifaBase = tarifaBase;
        this.descuentoAplicado = descuentoAplicado;
        this.tarifaFinal = tarifaFinal;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTramo() {
        return tramo;
    }

    public void setTramo(String tramo) {
        this.tramo = tramo;
    }

    public BigDecimal getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(BigDecimal tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public boolean isDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(boolean descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public BigDecimal getTarifaFinal() {
        return tarifaFinal;
    }

    public void setTarifaFinal(BigDecimal tarifaFinal) {
        this.tarifaFinal = tarifaFinal;
    }
}