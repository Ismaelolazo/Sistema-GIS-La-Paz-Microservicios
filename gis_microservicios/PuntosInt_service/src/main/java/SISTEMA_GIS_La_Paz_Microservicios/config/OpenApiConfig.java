package SISTEMA_GIS_La_Paz_Microservicios.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI puntosInteresOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Puntos de Interés API")
                        .description("API para la gestión de Puntos de Interés en el sistema GIS de La Paz")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Sistema GIS La Paz")
                                .url("https://github.com/Sistema-GIS-La-Paz-Microservicios"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server().url("/").description("Default Server URL")
                ));
    }
}
