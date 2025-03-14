package SISTEMA_GIS_La_Paz_Microservicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "SISTEMA_GIS_La_Paz_Microservicios")
@ComponentScan(basePackages = {
    "SISTEMA_GIS_La_Paz_Microservicios.PuntosInt_service",
    "SISTEMA_GIS_La_Paz_Microservicios.controller",
    "SISTEMA_GIS_La_Paz_Microservicios.config",
    "SISTEMA_GIS_La_Paz_Microservicios.dto",
    "SISTEMA_GIS_La_Paz_Microservicios.exception",
    "SISTEMA_GIS_La_Paz_Microservicios.service"  
})

public class PuntosIntServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PuntosIntServiceApplication.class, args);
    }
}
