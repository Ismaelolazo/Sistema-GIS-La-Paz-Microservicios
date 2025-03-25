package Sistema_GIS_La_Paz_Microservicios.Usuario_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Sistema_GIS_La_Paz_Microservicios.Usuario_service.dto.UsuarioDTO;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.service.UsuarioService;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.http.HttpErrorInfo;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.http.ResponseWrapper;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.http.ServiceUtil;

import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuario Management API", description = "REST APIs for managing users in the system")
@RestController
public class UsuarioController implements UsuarioControllerSwagger {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ServiceUtil serviceUtil;

	@Operation(summary = "Get all users", description = "Returns a list of all users in the system")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users",
	        content = @Content(mediaType = "application/json", 
	            schema = @Schema(implementation = ResponseWrapper.class))),
	    @ApiResponse(responseCode = "400", description = "Bad Request",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class))),
	    @ApiResponse(responseCode = "404", description = "No users found",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class))),
	    @ApiResponse(responseCode = "422", description = "Unprocessable Entity - Invalid parameters",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class)))
	})
	@GetMapping("/Usuario")
	public ResponseEntity<ResponseWrapper<List<Usuario>>> getAllusuarios() {
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("X-Custom-Header", "Lista de usuarios");
	    
	    List<Usuario> usuarios = usuarioService.getAllUsuarios();
	    ResponseWrapper<List<Usuario>> response = new ResponseWrapper<>(
	        serviceUtil.getServiceAddress(),
	        HttpStatus.OK.value(),
	        HttpStatus.OK.getReasonPhrase(),
	        usuarios
	    );
	    
	    return ResponseEntity.ok()
	        .headers(headers)
	        .body(response);
	}
	
	@Operation(summary = "Get user by ID", description = "Returns a single user by their ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
	        content = @Content(mediaType = "application/json", 
	            schema = @Schema(implementation = ResponseWrapper.class))),
	    @ApiResponse(responseCode = "400", description = "Invalid input",
	        content = @Content(mediaType = "application/json", 
	            schema = @Schema(implementation = HttpErrorInfo.class))),
	    @ApiResponse(responseCode = "404", description = "User not found",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class))),
	    @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class)))
	})
	@GetMapping("/Usuario/{id}")
	public ResponseEntity<ResponseWrapper<Usuario>> getUsuario(@PathVariable int id) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("X-Custom-Header", "Usuario individual");
	    
	    Usuario usuario = usuarioService.getUsuarioById(id);
	    ResponseWrapper<Usuario> response = new ResponseWrapper<>(
	        serviceUtil.getServiceAddress(),
	        HttpStatus.OK.value(),
	        HttpStatus.OK.getReasonPhrase(),
	        usuario
	    );
	    
	    return ResponseEntity.ok()
	        .headers(headers)
	        .body(response);
	}
	
	@Operation(summary = "Create new user", description = "Creates a new user in the system")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "User successfully created",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = ResponseWrapper.class))),
	    @ApiResponse(responseCode = "400", description = "Invalid input",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class))),
	    @ApiResponse(responseCode = "404", description = "Service not available",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class))),
	    @ApiResponse(responseCode = "409", description = "User already exists",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class))),
	    @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class)))
	})
	@PostMapping("/Usuario/add")
	public ResponseEntity<ResponseWrapper<Usuario>> createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("X-Custom-Header", "Usuario creado");
	    
	    Usuario newUsuario = usuarioService.createUsuario(usuarioDTO);
	    ResponseWrapper<Usuario> response = new ResponseWrapper<>(
	        serviceUtil.getServiceAddress(),
	        HttpStatus.CREATED.value(),
	        HttpStatus.CREATED.getReasonPhrase(),
	        newUsuario
	    );
	    
	    return ResponseEntity.status(HttpStatus.CREATED)
	        .headers(headers)
	        .body(response);
	}
	
	@Operation(summary = "Update user", description = "Updates an existing user by their ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "User successfully updated",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = ResponseWrapper.class))),
	    @ApiResponse(responseCode = "400", description = "Invalid input",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class))),
	    @ApiResponse(responseCode = "404", description = "User not found",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class))),
	    @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class)))
	})
	@PutMapping("/Usuario/update/{id}")
	public ResponseEntity<ResponseWrapper<Usuario>> updateusuarios(@PathVariable int id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("X-Custom-Header", "Usuario actualizado");
	    
	    Usuario updatedUsuario = usuarioService.updateUsuario(id, usuarioDTO);
	    ResponseWrapper<Usuario> response = new ResponseWrapper<>(
	        serviceUtil.getServiceAddress(),
	        HttpStatus.OK.value(),
	        HttpStatus.OK.getReasonPhrase(),
	        updatedUsuario
	    );
	    
	    return ResponseEntity.ok()
	        .headers(headers)
	        .body(response);
	}
	
	@Operation(summary = "Delete user", description = "Deletes a user from the system")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "User successfully deleted",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = ResponseWrapper.class))),
	    @ApiResponse(responseCode = "400", description = "Invalid user ID format",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class))),
	    @ApiResponse(responseCode = "404", description = "User not found",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class))),
	    @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = HttpErrorInfo.class)))
	})
	@DeleteMapping("/Usuario/delete/{id}")
	public ResponseEntity<ResponseWrapper<String>> removeUsuario(@PathVariable int id) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("X-Custom-Header", "Usuario eliminado");
	    
	    usuarioService.deleteUsuario(id);
	    String message = "Usuario with id " + id + " has been deleted";
	    ResponseWrapper<String> response = new ResponseWrapper<>(
	        serviceUtil.getServiceAddress(),
	        HttpStatus.OK.value(),
	        HttpStatus.OK.getReasonPhrase(),
	        message
	    );
	    
	    return ResponseEntity.ok()
	        .headers(headers)
	        .body(response);
	}

	@Override
	public ResponseWrapper<Usuario> UsuarioModel(@Valid Usuario model) {
		throw new UnsupportedOperationException("Unimplemented method 'UsuarioModel'");
	}
}
