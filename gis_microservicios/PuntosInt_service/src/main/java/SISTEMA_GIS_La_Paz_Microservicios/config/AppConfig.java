package SISTEMA_GIS_La_Paz_Microservicios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import SISTEMA_GIS_La_Paz_Microservicios.service.PuntoInteresService;

@Configuration
public class AppConfig {

    @Bean
    public PuntoInteresService puntoInteresService() {
        return new PuntoInteresService();
    }
}
