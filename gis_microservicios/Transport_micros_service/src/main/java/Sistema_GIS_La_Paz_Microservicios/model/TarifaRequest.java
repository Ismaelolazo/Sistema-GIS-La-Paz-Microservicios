package main.java.Sistema_GIS_La_Paz_Microservicios.model;

import jakarta.validation.constraints.NotNull;

public class TarifaRequest {
    
    @NotNull(message = "El tipo de horario no puede ser nulo")
    private TipoHorario tipoHorario;
    
    @NotNull(message = "El tipo de tramo no puede ser nulo")
    private TipoTramo tipoTramo;
    
    private boolean tarifaDiferencial;

    public TipoHorario getTipoHorario() {
        return tipoHorario;
    }

    public void setTipoHorario(TipoHorario tipoHorario) {
        this.tipoHorario = tipoHorario;
    }

    public TipoTramo getTipoTramo() {
        return tipoTramo;
    }

    public void setTipoTramo(TipoTramo tipoTramo) {
        this.tipoTramo = tipoTramo;
    }

    public boolean isTarifaDiferencial() {
        return tarifaDiferencial;
    }

    public void setTarifaDiferencial(boolean tarifaDiferencial) {
        this.tarifaDiferencial = tarifaDiferencial;
    }
}