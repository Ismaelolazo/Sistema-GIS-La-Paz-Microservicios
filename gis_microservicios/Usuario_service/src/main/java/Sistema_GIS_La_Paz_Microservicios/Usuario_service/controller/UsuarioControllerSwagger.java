package Sistema_GIS_La_Paz_Microservicios.Usuario_service.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;


@Tag(name = "Usuario Microservice", description = "This Microservice will Usuario the model passed in the response.")
public interface UsuarioControllerSwagger {

    @Operation(operationId = "UsuarioController", description = "This is a Description of this API call", summary = "This method will Usuario its request body")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)))
    public Usuario UsuarioModel(@RequestBody @Valid Usuario model);
}