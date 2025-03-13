package SISTEMA_GIS_La_Paz_Microservicios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PuntoInteresDTO {
    
    @NotNull
    private Integer id;
    
    @NotBlank
    @Size(min = 3, max = 100)
    private String nombre;
    
    @NotBlank
    @Size(min = 10, max = 255)
    private String descripcion;
    
    @NotNull
    private Double latitud;
    
    @NotNull
    private Double longitud;
    
    @NotBlank
    @Size(min = 3, max = 50)
    private String categoria;

    public PuntoInteresDTO() {}

    public PuntoInteresDTO(Integer id, String nombre, String descripcion, Double latitud, Double longitud, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.categoria = categoria;
    }

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
