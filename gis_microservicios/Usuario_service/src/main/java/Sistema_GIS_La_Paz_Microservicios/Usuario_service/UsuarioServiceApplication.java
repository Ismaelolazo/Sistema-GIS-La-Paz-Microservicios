package Sistema_GIS_La_Paz_Microservicios.Usuario_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
    info = @Info(
        title = "Usuario Service API",
        version = "1.0",
        description = "API for managing users in the system"
    ),
    tags = {
        @Tag(name = "Usuario Management API", description = "Operations for managing users"),
        @Tag(name = "Error Responses", description = "Standard error response formats")
    }
)
@SpringBootApplication
public class UsuarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioServiceApplication.class, args);
	}

}
