package SISTEMA_GIS_La_Paz_Microservicios.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Puntos_Interes")
public class PuntoInteres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Asegúrate de usar IDENTITY
    @Column(name = "ID_punto_interes")
    private Integer id;

    @Column(name = "Nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "Descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "Latitud", nullable = false)
    private Double latitud;

    @Column(name = "Longitud", nullable = false)
    private Double longitud;

    @Column(name = "Categoria", nullable = false, length = 50)
    private String categoria;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
