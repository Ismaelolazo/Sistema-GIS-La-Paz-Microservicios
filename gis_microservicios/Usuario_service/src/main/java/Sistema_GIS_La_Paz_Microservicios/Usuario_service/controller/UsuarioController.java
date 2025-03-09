package Sistema_GIS_La_Paz_Microservicios.Usuario_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Sistema_GIS_La_Paz_Microservicios.Usuario_service.dto.UsuarioDTO;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
public class UsuarioController implements UsuarioControllerSwagger {
	
	@Autowired
	UsuarioService usuarioService;

	@GetMapping("/Usuario")
	public List<Usuario> getAllusuarios(){
		return usuarioService.getAllUsuarios();
	}
	
	@GetMapping("/Usuario/{id}")
	public Usuario getUsuario(@PathVariable int id) {
		return usuarioService.getUsuarioById(id);
	}
	
	@PostMapping("/Usuario/add")
	public ResponseEntity<Usuario> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		Usuario newUsuario = usuarioService.createUsuario(usuarioDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(newUsuario);
	}
	
	@PutMapping("/Usuario/update/{id}")
	public Usuario updateusuarios(@PathVariable int id, @RequestBody UsuarioDTO usuarioDTO) {
		return usuarioService.updateUsuario(id, usuarioDTO);
	}
	
	@DeleteMapping("/Usuario/delete/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeUsuario(@PathVariable int id) {
		usuarioService.deleteUsuario(id);
	}

	@Override
	public Usuario UsuarioModel(@Valid Usuario model) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'UsuarioModel'");
	}
}
