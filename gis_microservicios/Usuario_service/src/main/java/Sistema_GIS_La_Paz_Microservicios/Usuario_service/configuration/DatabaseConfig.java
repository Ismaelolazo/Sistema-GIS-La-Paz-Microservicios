package Sistema_GIS_La_Paz_Microservicios.Usuario_service.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class DatabaseConfig {
    
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseConfig.class);
    
    @Value("${spring.datasource.url}")
    private String dbUrl;
    
    @Value("${spring.datasource.username}")
    private String dbUsername;
    
    @EventListener(ApplicationReadyEvent.class)
    public void logDbConfig() {
        LOG.info("Database configuration - URL: {}, Username: {}", dbUrl, dbUsername);
        LOG.info("Ensure PostgreSQL is running and accessible at the configured URL");
        LOG.info("For local development, you may need to modify the connection URL to use 'localhost' instead of 'postgres'");
    }
}
