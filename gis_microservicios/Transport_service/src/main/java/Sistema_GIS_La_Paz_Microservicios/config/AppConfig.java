package Sistema_GIS_La_Paz_Microservicios.config;

import Sistema_GIS_La_Paz_Microservicios.service.TarifaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    @Bean
    public TarifaService tarifaService() {
        return new TarifaService();
    }
}
