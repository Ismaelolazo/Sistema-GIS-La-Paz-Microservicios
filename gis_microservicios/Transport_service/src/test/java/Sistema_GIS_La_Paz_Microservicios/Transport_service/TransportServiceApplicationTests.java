package Sistema_GIS_La_Paz_Microservicios.Transport_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = TransportServiceApplicationTests.TestConfig.class)
class TransportServiceApplicationTests {

    @EnableAutoConfiguration
    @ComponentScan(basePackages = "Sistema_GIS_La_Paz_Microservicios")
    static class TestConfig {
        // Test configuration to load minimal context
    }
    
    @Test
    void contextLoads() {
        // Empty test to verify context loads properly
    }
}