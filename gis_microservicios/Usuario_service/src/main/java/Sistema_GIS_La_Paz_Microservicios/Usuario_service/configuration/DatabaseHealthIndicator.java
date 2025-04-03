package Sistema_GIS_La_Paz_Microservicios.Usuario_service.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseHealthIndicator {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseHealthIndicator.class);
    private final DataSource dataSource;

    @Autowired
    public DatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Checks the database connectivity
     * @return a map containing status details
     */
    public Map<String, Object> checkHealth() {
        Map<String, Object> details = new HashMap<>();
        details.put("database", "PostgreSQL");
        
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("SELECT 1");
                details.put("status", "Connected");
                LOG.info("Database connection successful");
            }
        } catch (SQLException e) {
            details.put("status", "Disconnected");
            details.put("error", e.getMessage());
            LOG.error("Database connection failed: {}", e.getMessage());
        }
        
        return details;
    }
}
