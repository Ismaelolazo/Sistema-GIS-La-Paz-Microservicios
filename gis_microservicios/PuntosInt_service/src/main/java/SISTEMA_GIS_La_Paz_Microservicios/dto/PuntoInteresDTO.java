package SISTEMA_GIS_La_Paz_Microservicios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PuntoInteresDTO {

    @Schema(example = "1", description = "Identificador único del punto de interés")
    private Integer id; // Puede ser null al agregar un nuevo punto

    @Schema(example = "Plaza Murillo", description = "Nombre del punto de interés")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Schema(example = "Plaza histórica en el centro de La Paz", description = "Descripción del punto de interés")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 255, message = "La descripción debe tener entre 10 y 255 caracteres")
    private String descripcion;

    @Schema(example = "-16.5000", description = "Latitud geográfica del punto de interés")
    private Double latitud;

    @Schema(example = "-68.1500", description = "Longitud geográfica del punto de interés")
    private Double longitud;

    @Schema(example = "Histórico", description = "Categoría del punto de interés")
    @NotBlank(message = "La categoría no puede estar vacía")
    @Size(min = 3, max = 50, message = "La categoría debe tener entre 3 y 50 caracteres")
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
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
