package Sistema_GIS_La_Paz_Microservicios.Usuario_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import Sistema_GIS_La_Paz_Microservicios.Usuario_service.dto.UsuarioDTO;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.exception.UsuarioNotFoundException;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.repository.UsuarioRepo;
import jakarta.validation.Valid;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepo.findAll();
    }

    public Usuario getUsuarioById(int id) {
        return usuarioRepo.findById(id).orElseThrow(() -> new UsuarioNotFoundException("Usuario not found with id: " + id));
    }

    public Usuario createUsuario(@Valid UsuarioDTO usuarioDTO) {
        if (usuarioRepo.existsByEmail(usuarioDTO.getEmail())) {
            throw new DataIntegrityViolationException("El email ya está en uso");
        }
        Usuario usuario = new Usuario(usuarioDTO.getId(), usuarioDTO.getName(), usuarioDTO.getEmail(), usuarioDTO.getPassword());
        return usuarioRepo.save(usuario);
    }

    public Usuario updateUsuario(int id, @Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepo.findById(id).orElseThrow(() -> new UsuarioNotFoundException("Usuario not found with id: " + id));
        usuario.setName(usuarioDTO.getName());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        return usuarioRepo.save(usuario);
    }

    public void deleteUsuario(int id) {
        Usuario usuario = usuarioRepo.findById(id).orElseThrow(() -> new UsuarioNotFoundException("Usuario not found with id: " + id));
        usuarioRepo.delete(usuario);
    }
}
