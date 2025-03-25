package Sistema_GIS_La_Paz_Microservicios.Usuario_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for User operations")
public class UsuarioDTO {

    @Schema(description = "User ID", example = "1", required = true)
    @NotNull(message = "El ID no puede ser nulo")
    @Min(value = 1, message = "El ID debe ser mayor que 0")
    @Max(value = 1000, message = "El ID no puede ser mayor que 1000")
    private int id;

    @Schema(description = "User name", example = "John Doe", required = true)
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @Schema(description = "User email", example = "john.doe@example.com", required = true)
    @NotNull(message = "El email no puede ser nulo")
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede tener más de 100 caracteres")
    private String email;

    @Schema(description = "User password", example = "password123", required = true)
    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 6, max = 20, message = "La contraseña debe tener entre 6 y 20 caracteres")
    private String password;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}