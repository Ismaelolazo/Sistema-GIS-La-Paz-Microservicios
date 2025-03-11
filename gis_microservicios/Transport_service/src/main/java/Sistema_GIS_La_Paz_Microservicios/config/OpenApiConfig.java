package Sistema_GIS_La_Paz_Microservicios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI transportServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Transporte API")
                        .description("API para el servicio de transporte del GIS La Paz")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Sistema GIS La Paz")
                                .url("https://github.com/Sistema-GIS-La-Paz-Microservicios")))
                .servers(List.of(
                        new Server().url("/").description("Default Server URL")
                ));
    }
}
