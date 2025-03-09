package Sistema_GIS_La_Paz_Microservicios.Usuario_service.controller;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.response.UsuarioResponse;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService UsuarioService;

    @GetMapping("/Usuarios/{id}")
    private ResponseEntity<UsuarioResponse> getUsuarioDetails(@PathVariable("id") int id) {
        UsuarioResponse Usuario = UsuarioService.getUsuarioById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Usuario);
    }

}