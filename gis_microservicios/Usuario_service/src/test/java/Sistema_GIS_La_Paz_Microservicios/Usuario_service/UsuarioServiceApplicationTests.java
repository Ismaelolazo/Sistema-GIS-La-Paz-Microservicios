package Sistema_GIS_La_Paz_Microservicios.Usuario_service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import Sistema_GIS_La_Paz_Microservicios.Usuario_service.dto.UsuarioDTO;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.repository.UsuarioRepo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=password",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class UsuarioServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepo repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setupDb() {
        repository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getUsuarioById() throws Exception {
        int usuarioId = 1;
        postAndVerifyUsuario(usuarioId, HttpStatus.CREATED);
        assertTrue(repository.findById(usuarioId).isPresent());
        
        mockMvc.perform(get("/Usuario/" + usuarioId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(usuarioId));
    }

    @Test
    void duplicateError() throws Exception {
        int usuarioId = 1;

        postAndVerifyUsuario(usuarioId, HttpStatus.CREATED);
        assertTrue(repository.findById(usuarioId).isPresent());

        mockMvc.perform(post("/Usuario/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUsuarioDTO(usuarioId, "User " + usuarioId, "user" + usuarioId + "@example.com", "password123")))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.path").value("/Usuario/add"));
    }

    @Test
    void deleteUsuario() throws Exception {
        int usuarioId = 1;

        postAndVerifyUsuario(usuarioId, HttpStatus.CREATED);
        assertTrue(repository.findById(usuarioId).isPresent());

        deleteAndVerifyUsuario(usuarioId, HttpStatus.OK);
        assertFalse(repository.findById(usuarioId).isPresent());

        deleteAndVerifyUsuario(usuarioId, HttpStatus.NOT_FOUND);
    }

    @Test
    void getUsuarioInvalidParameterString() throws Exception {
        mockMvc.perform(get("/Usuario/no-integer")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.path").value("/Usuario/no-integer"))
            .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    void getUsuarioNotFound() throws Exception {
        int usuarioIdNotFound = 13;
        mockMvc.perform(get("/Usuario/" + usuarioIdNotFound)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.path").value("/Usuario/" + usuarioIdNotFound));
    }

    @Test
    void getUsuarioInvalidParameterNegativeValue() throws Exception {
        int usuarioIdInvalid = -1;

        mockMvc.perform(get("/Usuario/" + usuarioIdInvalid)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.path").value("/Usuario/" + usuarioIdInvalid));
    }

    @Test
    void getAllUsuarios() throws Exception {
        // Create multiple users
        postAndVerifyUsuario(1, HttpStatus.CREATED);
        postAndVerifyUsuario(2, HttpStatus.CREATED);
        
        // Get all and verify length
        mockMvc.perform(get("/Usuario")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.length()").value(2))
            .andExpect(jsonPath("$.data[0].id").value(1))
            .andExpect(jsonPath("$.data[1].id").value(2));
    }

    @Test
    void updateUsuario() throws Exception {
        int usuarioId = 1;
        
        // Create initial user
        postAndVerifyUsuario(usuarioId, HttpStatus.CREATED);
        assertTrue(repository.findById(usuarioId).isPresent());
        
        // Update user with new information
        UsuarioDTO updatedUsuario = createUsuarioDTO(usuarioId, "Jane Doe", "jane.doe@example.com", "newpassword123");
        
        mockMvc.perform(put("/Usuario/update/" + usuarioId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUsuario))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.name").value("Jane Doe"))
            .andExpect(jsonPath("$.data.email").value("jane.doe@example.com"));
    }

    private MvcResult postAndVerifyUsuario(int usuarioId, HttpStatus expectedStatus) throws Exception {
        UsuarioDTO usuario = createUsuarioDTO(usuarioId, "User " + usuarioId, "user" + usuarioId + "@example.com", "password123");
        return mockMvc.perform(post("/Usuario/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(expectedStatus.value()))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    }

    private MvcResult deleteAndVerifyUsuario(int usuarioId, HttpStatus expectedStatus) throws Exception {
        return mockMvc.perform(delete("/Usuario/delete/" + usuarioId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(expectedStatus.value()))
            .andReturn();
    }

    private UsuarioDTO createUsuarioDTO(int id, String name, String email, String password) {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(id);
        usuario.setName(name);
        usuario.setEmail(email);
        usuario.setPassword(password);
        return usuario;
    }
}
