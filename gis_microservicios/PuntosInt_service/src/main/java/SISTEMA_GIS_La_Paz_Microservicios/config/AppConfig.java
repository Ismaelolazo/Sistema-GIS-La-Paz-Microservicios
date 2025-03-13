/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package SISTEMA_GIS_La_Paz_Microservicios.config;

import SISTEMA_GIS_La_Paz_Microservicios.service.PuntoInteresService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public PuntoInteresService puntoInteresService() {
        return new PuntoInteresService();
    }
}
