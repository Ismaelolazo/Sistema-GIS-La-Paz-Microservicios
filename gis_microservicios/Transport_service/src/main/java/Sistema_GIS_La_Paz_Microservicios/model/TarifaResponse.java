package Sistema_GIS_La_Paz_Microservicios.model;

public class TarifaResponse {
    private String tipoVehiculo;
    private String horario;
    private String tramo;
    private double tarifaBase;
    private boolean descuentoAplicado;
    private double tarifaFinal;

    public TarifaResponse() {
    }

    public TarifaResponse(String tipoVehiculo, String horario, String tramo, double tarifaBase, boolean descuentoAplicado, double tarifaFinal) {
        this.tipoVehiculo = tipoVehiculo;
        this.horario = horario;
        this.tramo = tramo;
        this.tarifaBase = tarifaBase;
        this.descuentoAplicado = descuentoAplicado;
        this.tarifaFinal = tarifaFinal;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
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

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public boolean isDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(boolean descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public double getTarifaFinal() {
        return tarifaFinal;
    }

    public void setTarifaFinal(double tarifaFinal) {
        this.tarifaFinal = tarifaFinal;
    }
}