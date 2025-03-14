package Sistema_GIS_La_Paz_Microservicios.Usuario_service.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Sistema_GIS_La_Paz_Microservicios.Usuario_service.dto.UsuarioDTO;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.service.UsuarioService;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.exception.ResourceNotFoundException;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.exception.UsuarioNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;

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
	public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
		Usuario newUsuario = usuarioService.createUsuario(usuarioDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(newUsuario);
	}
	
	@PutMapping("/Usuario/update/{id}")
	public Usuario updateusuarios(@PathVariable int id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
		return usuarioService.updateUsuario(id, usuarioDTO);
	}
	
	@DeleteMapping("/Usuario/delete/{id}")
	public ResponseEntity<String> removeUsuario(@PathVariable int id) {
		usuarioService.deleteUsuario(id);
		return ResponseEntity.status(HttpStatus.OK).body("Usuario with id " + id + " has been deleted.");
	}

	@Override
	public Usuario UsuarioModel(@Valid Usuario model) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'UsuarioModel'");//Es simplemente un marcador de posición a la espera de una implementación adecuada.
	}

	@ExceptionHandler(UsuarioNotFoundException.class)
	public ResponseEntity<String> handleUsuarioNotFoundException(UsuarioNotFoundException ex) {// Este método maneja las excepcion cuando no enceuntra un usuario
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getFieldErrors().forEach(error -> 
	        errors.put(error.getField(), error.getDefaultMessage()));
	    return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {// Usuario no existente
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {// email ya en uso
	    return ResponseEntity.status(HttpStatus.CONFLICT).body("Database error: " + ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {// excepcion de Usuario/abc
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Type mismatch error: " + ex.getMessage());
	}
}
