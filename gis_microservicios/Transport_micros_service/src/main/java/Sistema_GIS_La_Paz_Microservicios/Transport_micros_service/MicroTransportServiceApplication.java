package main.java.Sistema_GIS_La_Paz_Microservicios.Transport_micros_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "Sistema_GIS_La_Paz_Microservicios.controller",
    "Sistema_GIS_La_Paz_Microservicios.service",
    "Sistema_GIS_La_Paz_Microservicios.model",
    "Sistema_GIS_La_Paz_Microservicios.config",
    "Sistema_GIS_La_Paz_Microservicios.Transport_micros_service"
})
public class MicroTransportServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroTransportServiceApplication.class, args);
    }
}