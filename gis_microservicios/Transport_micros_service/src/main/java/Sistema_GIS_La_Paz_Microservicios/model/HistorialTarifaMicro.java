package main.java.Sistema_GIS_La_Paz_Microservicios.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Historial_Tarifas_Micro")
public class HistorialTarifaMicro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "tipo_tramo", nullable = false)
    private String tipoTramo;
    
    @Column(name = "tipo_horario", nullable = false)
    private String tipoHorario;
    
    @Column(name = "tarifa_base", nullable = false)
    private BigDecimal tarifaBase;
    
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;
    
    @Column(name = "id_usuario")
    private Integer idUsuario;

    // Constructors
    public HistorialTarifaMicro() {
    }
    
    public HistorialTarifaMicro(String tipoTramo, String tipoHorario, BigDecimal tarifaBase, LocalDateTime fechaInicio) {
        this.tipoTramo = tipoTramo;
        this.tipoHorario = tipoHorario;
        this.tarifaBase = tarifaBase;
        this.fechaInicio = fechaInicio;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoTramo() {
        return tipoTramo;
    }

    public void setTipoTramo(String tipoTramo) {
        this.tipoTramo = tipoTramo;
    }

    public String getTipoHorario() {
        return tipoHorario;
    }

    public void setTipoHorario(String tipoHorario) {
        this.tipoHorario = tipoHorario;
    }

    public BigDecimal getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(BigDecimal tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}