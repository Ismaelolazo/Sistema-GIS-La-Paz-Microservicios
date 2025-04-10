package Sistema_GIS_La_Paz_Microservicios.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tarifas_Transporte")
public class TarifaMicro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_tarifa")
    private Long id;
    
    @Column(name = "ID_ruta")
    private Integer idRuta;
    
    @Column(name = "Tipo_vehiculo")
    private String tipoVehiculo = "Minibús"; // Para el microservicio de micros siempre será Minibús
    
    @Column(name = "Horario")
    private String horario;
    
    @Column(name = "Tramo")
    private String tramo;
    
    @Column(name = "Condicion_usuario")
    private String condicionUsuario = "General"; // Por defecto es tarifa general
    
    @Column(name = "Precio")
    private BigDecimal precio;
    
    @Transient // No está en la tabla, lo usamos para compatibilidad
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    // Constructors
    public TarifaMicro() {
    }
    
    public TarifaMicro(Integer idRuta, String horario, String tramo, String condicionUsuario, BigDecimal precio) {
        this.idRuta = idRuta;
        this.horario = horario;
        this.tramo = tramo;
        this.condicionUsuario = condicionUsuario;
        this.precio = precio;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
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

    public String getCondicionUsuario() {
        return condicionUsuario;
    }

    public void setCondicionUsuario(String condicionUsuario) {
        this.condicionUsuario = condicionUsuario;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    // Métodos de compatibilidad con el código existente
    public String getTipoHorario() {
        return horario;
    }
    
    public void setTipoHorario(String tipoHorario) {
        this.horario = tipoHorario;
    }
    
    public String getTipoTramo() {
        return tramo;
    }
    
    public void setTipoTramo(String tipoTramo) {
        this.tramo = tipoTramo;
    }
    
    public BigDecimal getTarifaBase() {
        return precio;
    }
    
    public void setTarifaBase(BigDecimal tarifaBase) {
        this.precio = tarifaBase;
    }
}