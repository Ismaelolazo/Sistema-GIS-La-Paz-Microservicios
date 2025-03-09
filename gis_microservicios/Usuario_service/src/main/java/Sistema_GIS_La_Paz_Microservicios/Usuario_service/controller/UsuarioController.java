
package Sistema_GIS_La_Paz_Microservicios.Usuario_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.repository.UsuarioRepo;

@RestController
public class UsuarioController {
	
	@Autowired
	UsuarioRepo repo;
	//get all the usuarios 
	//localhost:8080/usuarios
	@GetMapping("/Usuario")
	public List<Usuario> getAllusuarios(){
		 List<Usuario> usuarios = repo.findAll();
		  return usuarios;
	}
	
	//localhost:8080/usuarios/1
	@GetMapping("/Usuario/{id}")
	public Usuario getUsuario(@PathVariable int id) {
		Usuario Usuario = repo.findById(id).get();
		
		return Usuario;
		
	}
	
	@PostMapping("/Usuario/add")
	public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario Usuario) {
		Usuario newUsuario = repo.save(Usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(newUsuario);
		
	}
	
	@PutMapping("/Usuario/update/{id}")
	public Usuario updateusuarios(@PathVariable int id) {
	   Usuario Usuario = repo.findById(id).get();
	   Usuario.setName("test usuario update");
	   Usuario.setEmail("test email update");
	   Usuario.setPassword("test contra update");
	   repo.save(Usuario);
	   return Usuario;
		
	}
	@DeleteMapping("/Usuario/delete/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeUsuario(@PathVariable int id) {
		Usuario Usuario = repo.findById(id).get();
		repo.delete(Usuario);
	}

}
