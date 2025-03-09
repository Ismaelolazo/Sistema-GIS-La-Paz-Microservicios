package Sistema_GIS_La_Paz_Microservicios.Usuario_service.service;


import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.repository.UsuarioRepo;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.response.UsuarioResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UsuarioService {

    @Autowired
    private UsuarioRepo UsuarioRepo;

    @Autowired
    private ModelMapper mapper;

    public UsuarioResponse getUsuarioById(int id) {
        Optional<Usuario> Usuario = UsuarioRepo.findById(id);
        UsuarioResponse UsuarioResponse = mapper.map(Usuario, UsuarioResponse.class);
        return UsuarioResponse;
    }

}