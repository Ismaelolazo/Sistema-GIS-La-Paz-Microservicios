package Sistema_GIS_La_Paz_Microservicios.Usuario_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Sistema_GIS_La_Paz_Microservicios.Usuario_service.dto.UsuarioDTO;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.repository.UsuarioRepo;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepo.findAll();
    }

    public Usuario getUsuarioById(int id) {
        return usuarioRepo.findById(id).orElse(null);
    }

    public Usuario createUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO.getId(), usuarioDTO.getName(), usuarioDTO.getEmail(), usuarioDTO.getPassword());
        return usuarioRepo.save(usuario);
    }

    public Usuario updateUsuario(int id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepo.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setName(usuarioDTO.getName());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setPassword(usuarioDTO.getPassword());
            usuarioRepo.save(usuario);
        }
        return usuario;
    }

    public void deleteUsuario(int id) {
        Usuario usuario = usuarioRepo.findById(id).orElse(null);
        if (usuario != null) {
            usuarioRepo.delete(usuario);
        }
    }
}
