package Sistema_GIS_La_Paz_Microservicios.Usuario_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import Sistema_GIS_La_Paz_Microservicios.Usuario_service.dto.UsuarioDTO;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.repository.UsuarioRepo;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.service.UsuarioService;

@SpringBootTest
@DirtiesContext
class UsuarioServiceIntegrationTests extends PostgreSqlTestBase {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @BeforeEach
    void cleanup() {
        usuarioRepo.deleteAll();
    }

    @Test
    void testCreateAndGetUsuario() {
        // Create test data
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1);
        usuarioDTO.setName("Test User");
        usuarioDTO.setEmail("test@example.com");
        usuarioDTO.setPassword("password123");

        // Create usuario
        Usuario created = usuarioService.createUsuario(usuarioDTO);
        
        // Verify created
        assertNotNull(created);
        assertEquals(1, created.getId());
        assertEquals("Test User", created.getName());
        
        // Get by ID and verify
        Usuario retrieved = usuarioService.getUsuarioById(1);
        assertNotNull(retrieved);
        assertEquals(1, retrieved.getId());
        assertEquals("Test User", retrieved.getName());
        assertEquals("test@example.com", retrieved.getEmail());
    }
    
    @Test
    void testUpdateUsuario() {
        // Create initial user
        UsuarioDTO createDTO = new UsuarioDTO();
        createDTO.setId(2);
        createDTO.setName("Original Name");
        createDTO.setEmail("original@example.com");
        createDTO.setPassword("password123");
        
        Usuario created = usuarioService.createUsuario(createDTO);
        assertNotNull(created);
        
        // Update data
        UsuarioDTO updateDTO = new UsuarioDTO();
        updateDTO.setId(2);
        updateDTO.setName("Updated Name");
        updateDTO.setEmail("updated@example.com");
        updateDTO.setPassword("newpassword123");
        
        Usuario updated = usuarioService.updateUsuario(2, updateDTO);
        
        // Verify update
        assertNotNull(updated);
        assertEquals(2, updated.getId());
        assertEquals("Updated Name", updated.getName());
        assertEquals("updated@example.com", updated.getEmail());
        
        // Retrieve from database to confirm persistence
        assertTrue(usuarioRepo.findById(2).isPresent());
        Usuario fromDb = usuarioRepo.findById(2).get();
        assertEquals("Updated Name", fromDb.getName());
    }
}
