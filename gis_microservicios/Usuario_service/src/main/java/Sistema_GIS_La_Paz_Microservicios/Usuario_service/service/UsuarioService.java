package Sistema_GIS_La_Paz_Microservicios.Usuario_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import Sistema_GIS_La_Paz_Microservicios.Usuario_service.dto.UsuarioDTO;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.exception.InvalidInputException;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.exception.NotFoundException;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.repository.UsuarioRepo;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuario Service", description = "Service layer for managing user operations")
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepo.findAll();
    }

    public Usuario getUsuarioById(int id) {
        return usuarioRepo.findById(id).orElseThrow(() -> new NotFoundException("Usuario not found with id: " + id));
    }

    public Usuario createUsuario(@Valid UsuarioDTO usuarioDTO) {
        validateUsuarioInput(usuarioDTO);
        if (usuarioRepo.existsByEmail(usuarioDTO.getEmail())) {
            throw new DataIntegrityViolationException("El email ya está en uso");
        }
        Usuario usuario = new Usuario(usuarioDTO.getId(), usuarioDTO.getName(), usuarioDTO.getEmail(), usuarioDTO.getPassword());
        return usuarioRepo.save(usuario);
    }

    public Usuario updateUsuario(int id, @Valid UsuarioDTO usuarioDTO) {
        validateUsuarioInput(usuarioDTO);
        Usuario usuario = usuarioRepo.findById(id).orElseThrow(() -> new NotFoundException("Usuario not found with id: " + id));
        usuario.setName(usuarioDTO.getName());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        return usuarioRepo.save(usuario);
    }

    private void validateUsuarioInput(UsuarioDTO usuarioDTO) {
        // Validación de ID
        if (usuarioDTO.getId() < 0) {
            throw new InvalidInputException("El ID no puede ser negativo");
        }
        
        // Validación de nombre
        if (usuarioDTO.getName() == null) {
            throw new InvalidInputException("El nombre no puede ser nulo");
        }
        if (usuarioDTO.getName().length() < 2 || usuarioDTO.getName().length() > 50) {
            throw new InvalidInputException("El nombre debe tener entre 2 y 50 caracteres");
        }
        
        // Validación de email
        if (usuarioDTO.getEmail() == null) {
            throw new InvalidInputException("El email no puede ser nulo");
        }
        if (!usuarioDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidInputException("El formato del email no es válido");
        }
        if (usuarioDTO.getEmail().length() > 100) {
            throw new InvalidInputException("El email excede los 100 caracteres permitidos");
        }
        
        // Validación de contraseña
        if (usuarioDTO.getPassword() == null) {
            throw new InvalidInputException("La contraseña no puede ser nula");
        }
        if (usuarioDTO.getPassword().length() < 6 || usuarioDTO.getPassword().length() > 20) {
            throw new InvalidInputException("La contraseña debe tener entre 6 y 20 caracteres");
        }
    }

    public void deleteUsuario(int id) {
        Usuario usuario = usuarioRepo.findById(id).orElseThrow(() -> new NotFoundException("Usuario not found with id: " + id));
        usuarioRepo.delete(usuario);
    }
}
