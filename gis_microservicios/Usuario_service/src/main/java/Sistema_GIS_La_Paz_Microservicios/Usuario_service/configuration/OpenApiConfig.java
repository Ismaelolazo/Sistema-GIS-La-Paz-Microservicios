package Sistema_GIS_La_Paz_Microservicios.Usuario_service.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.http.HttpErrorInfo;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.http.ResponseWrapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI userServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Usuario Service API")
                        .description("API para gestión de usuarios en el sistema GIS La Paz")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipo GIS La Paz")
                                .email("contacto@gislapaz.org"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .components(new Components()
                        .addSchemas("HttpErrorInfo", new Schema<HttpErrorInfo>()
                                .type("object")
                                .required(List.of("timestamp", "path", "status", "error", "message"))
                                .addProperties("timestamp", new Schema<>().type("string").format("date-time"))
                                .addProperties("path", new Schema<>().type("string"))
                                .addProperties("status", new Schema<>().type("integer").format("int32"))
                                .addProperties("error", new Schema<>().type("string"))
                                .addProperties("message", new Schema<>().type("string"))
                                .addProperties("details", new Schema<Map<String, Object>>().type("object"))
                                .example("""
                                    {
                                      "timestamp": "2023-11-19T22:14:20.905Z",
                                      "path": "/Usuario/1",
                                      "status": 404,
                                      "error": "Not Found",
                                      "message": "Usuario not found with id: 1",
                                      "details": {
                                        "error": "El recurso solicitado no existe"
                                      }
                                    }
                                    """))
                        .addSchemas("ResponseWrapper", new Schema<ResponseWrapper<?>>()
                                .type("object")
                                .required(List.of("timestamp", "path", "status", "error", "data"))
                                .addProperties("timestamp", new Schema<>().type("string").format("date-time"))
                                .addProperties("path", new Schema<>().type("string"))
                                .addProperties("status", new Schema<>().type("integer").format("int32"))
                                .addProperties("error", new Schema<>().type("string"))
                                .addProperties("data", new Schema<>().type("object"))
                                .example("""
                                    {
                                      "timestamp": "2023-11-19T22:14:20.905Z",
                                      "path": "/Usuario/1",
                                      "status": 200,
                                      "error": "OK",
                                      "data": {
                                        "id": 1,
                                        "name": "John Doe",
                                        "email": "john@example.com",
                                        "password": "password123"
                                      }
                                    }
                                    """))
                        .addResponses("NotFound", new ApiResponse()
                                .description("Recurso no encontrado")
                                .content(new Content().addMediaType("application/json", 
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/HttpErrorInfo")))))
                        .addResponses("BadRequest", new ApiResponse()
                                .description("Solicitud inválida")
                                .content(new Content().addMediaType("application/json", 
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/HttpErrorInfo")))))
                        .addResponses("UnprocessableEntity", new ApiResponse()
                                .description("Entidad no procesable")
                                .content(new Content().addMediaType("application/json", 
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/HttpErrorInfo")))))
                        .addResponses("Conflict", new ApiResponse()
                                .description("Conflicto de recursos")
                                .content(new Content().addMediaType("application/json", 
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/HttpErrorInfo"))))));
    }
}