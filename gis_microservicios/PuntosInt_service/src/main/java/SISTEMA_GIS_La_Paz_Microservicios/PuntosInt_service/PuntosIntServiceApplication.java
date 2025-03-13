package SISTEMA_GIS_La_Paz_Microservicios.PuntosInt_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "SISTEMA_GIS_La_Paz_Microservicios.PuntosInt_service",
    "SISTEMA_GIS_La_Paz_Microservicios.controller",
    "SISTEMA_GIS_La_Paz_Microservicios.config" 
})
public class PuntosIntServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PuntosIntServiceApplication.class, args);
    }
}
