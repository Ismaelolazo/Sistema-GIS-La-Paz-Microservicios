package Sistema_GIS_La_Paz_Microservicios.Usuario_service.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class DatabaseConnectionCheckerScheduler {

    @Autowired
    private DatabaseHealthIndicator databaseHealthIndicator;

    /**
     * Runs the database health check every 5 minutes
     */
    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void checkDatabaseConnection() {
        databaseHealthIndicator.checkHealth();
    }
}
