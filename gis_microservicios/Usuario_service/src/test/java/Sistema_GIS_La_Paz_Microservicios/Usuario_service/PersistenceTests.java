package Sistema_GIS_La_Paz_Microservicios.Usuario_service;

import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.repository.UsuarioRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class PersistenceTests extends PostgreSqlTestBase {
    
    @Autowired
    private UsuarioRepo repository;

    private Usuario savedEntity;

    @BeforeEach
    void setupDb() {
        repository.deleteAll();

        Usuario entity = new Usuario(1, "Test User", "test@example.com", "password123");
        savedEntity = repository.save(entity);

        assertEqualsUsuario(entity, savedEntity);
    }

    @Test
    void create() {
        Usuario newEntity = new Usuario(2, "New User", "new@example.com", "password456");
        repository.save(newEntity);

        Optional<Usuario> foundEntity = repository.findById(newEntity.getId());
        assertTrue(foundEntity.isPresent());
        assertEqualsUsuario(newEntity, foundEntity.get());

        assertEquals(2, repository.count());
    }

    @Test
    void update() {
        savedEntity.setName("Updated Name");
        repository.save(savedEntity);

        Optional<Usuario> foundEntity = repository.findById(savedEntity.getId());
        assertTrue(foundEntity.isPresent());
        assertEquals("Updated Name", foundEntity.get().getName());
    }

    @Test
    void delete() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    void findById() {
        Optional<Usuario> entity = repository.findById(savedEntity.getId());

        assertTrue(entity.isPresent());
        assertEqualsUsuario(savedEntity, entity.get());
    }

    @Test
    void duplicateEmailError() {
        // Try to create another user with the same email
        assertThrows(DataIntegrityViolationException.class, () -> {
            Usuario entity = new Usuario(2, "Another User", savedEntity.getEmail(), "anotherpassword");
            repository.saveAndFlush(entity); // Using saveAndFlush to immediately execute the SQL
        });
    }

    @Test
    void paging() {
        repository.deleteAll();

        List<Usuario> newUsuarios = rangeClosed(1, 10)
            .mapToObj(i -> new Usuario(i, "User " + i, "user" + i + "@example.com", "password" + i))
            .collect(Collectors.toList());
        repository.saveAll(newUsuarios);

        Pageable nextPage = PageRequest.of(0, 4, Sort.Direction.ASC, "id");
        nextPage = testNextPage(nextPage, "[1, 2, 3, 4]", true);
        nextPage = testNextPage(nextPage, "[5, 6, 7, 8]", true);
        nextPage = testNextPage(nextPage, "[9, 10]", false);
    }

    @Test
    void customQueryExists() {
        // Test the existsByEmail custom query method
        assertTrue(repository.existsByEmail(savedEntity.getEmail()));
        assertFalse(repository.existsByEmail("nonexistent@example.com"));
    }

    @Test
    void findMultipleUsers() {
        repository.deleteAll();

        // Create 15 users
        List<Usuario> newUsuarios = rangeClosed(1, 15)
            .mapToObj(i -> {
                String email = i % 2 == 0 ? "even" + i + "@example.com" : "odd" + i + "@example.com";
                return new Usuario(i, "User " + i, email, "password" + i);
            })
            .collect(Collectors.toList());
        repository.saveAll(newUsuarios);

        // Test finding all users
        List<Usuario> allUsers = repository.findAll();
        assertEquals(15, allUsers.size());
        
        // Test sorting
        List<Usuario> sortedUsers = repository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        assertEquals("User 9", sortedUsers.get(6).getName()); // With DESC sort, "User 9" should be at position 6
    }

    private Pageable testNextPage(Pageable nextPage, String expectedIds, boolean expectsNextPage) {
        Page<Usuario> usuarioPage = repository.findAll(nextPage);
        assertEquals(expectedIds, usuarioPage.getContent().stream().map(p -> p.getId()).collect(Collectors.toList()).toString());
        assertEquals(expectsNextPage, usuarioPage.hasNext());
        return usuarioPage.nextPageable();
    }

    private void assertEqualsUsuario(Usuario expectedEntity, Usuario actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getName(), actualEntity.getName());
        assertEquals(expectedEntity.getEmail(), actualEntity.getEmail());
        assertEquals(expectedEntity.getPassword(), actualEntity.getPassword());
    }
}
