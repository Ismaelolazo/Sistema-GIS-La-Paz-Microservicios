package Sistema_GIS_La_Paz_Microservicios.Usuario_service;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class PostgreSqlTestBase {
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("usuario-test")
        .withUsername("test-user")
        .withPassword("test-password");
  
    static {
        database.start();
    } 
  
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }
}
