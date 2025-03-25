package Sistema_GIS_La_Paz_Microservicios.Usuario_service.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.http.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;

@Tag(name = "Usuario Management API", description = "REST APIs for managing users in the system")
public interface UsuarioControllerSwagger {

    @Operation(operationId = "UsuarioModel", 
              summary = "Create user model", 
              description = "Creates a new user model based on the provided data")
    @ApiResponse(responseCode = "200", 
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ResponseWrapper.class)))
    public ResponseWrapper<Usuario> UsuarioModel(@RequestBody @Valid Usuario model);
}