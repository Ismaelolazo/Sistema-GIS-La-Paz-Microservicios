package Sistema_GIS_La_Paz_Microservicios.Transport_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "Sistema_GIS_La_Paz_Microservicios.Transport_service",
    "Sistema_GIS_La_Paz_Microservicios.controller"
})
public class TransportServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransportServiceApplication.class, args);
    }
}
