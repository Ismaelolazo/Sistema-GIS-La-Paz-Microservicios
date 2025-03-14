package SISTEMA_GIS_La_Paz_Microservicios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PuntoInteresDTO {

    @Schema(example = "1", description = "Identificador único del punto de interés")
    @NotNull
    private Integer id;

    @Schema(example = "Plaza Murillo", description = "Nombre del punto de interés")
    @NotBlank
    @Size(min = 3, max = 100)
    private String nombre;

    @Schema(example = "Plaza histórica en el centro de La Paz", description = "Descripción del punto de interés")
    @NotBlank
    @Size(min = 10, max = 255)
    private String descripcion;

    @Schema(example = "-16.5000", description = "Latitud geográfica del punto de interés")
    @NotNull
    private Double latitud;

    @Schema(example = "-68.1500", description = "Longitud geográfica del punto de interés")
    @NotNull
    private Double longitud;

    @Schema(example = "Histórico", description = "Categoría del punto de interés")
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
