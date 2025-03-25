package Sistema_GIS_La_Paz_Microservicios.Usuario_service.http;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import Sistema_GIS_La_Paz_Microservicios.Usuario_service.exception.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Error Handler", description = "Global exception handler for HTTP errors")
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private final ServiceUtil serviceUtil;

    public GlobalControllerExceptionHandler(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Resource not found",
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = HttpErrorInfo.class)))
    public HttpErrorInfo handleNotFoundException(NotFoundException ex) {
        return createHttpErrorInfo(HttpStatus.NOT_FOUND, ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    @ApiResponse(responseCode = "400", description = "Bad Request",
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = HttpErrorInfo.class)))
    public HttpErrorInfo handleBadRequestException(Exception ex) {
        return createHttpErrorInfo(HttpStatus.BAD_REQUEST, ex);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = HttpErrorInfo.class)))
    public HttpErrorInfo handleInvalidInputException(InvalidInputException ex) {
        return createHttpErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY, ex);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(responseCode = "400", description = "Validation Error",
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = HttpErrorInfo.class)))
    public HttpErrorInfo handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            details.put(error.getField(), error.getDefaultMessage()));
        
        return new HttpErrorInfo(
            serviceUtil.getServiceAddress(),
            HttpStatus.BAD_REQUEST,
            "Validation failed",
            details
        );
    }
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ApiResponse(responseCode = "409", description = "Resource Conflict",
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = HttpErrorInfo.class)))
    public HttpErrorInfo handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return createHttpErrorInfo(HttpStatus.CONFLICT, ex);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ApiResponse(responseCode = "400", description = "Type Mismatch Error",
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = HttpErrorInfo.class)))
    public HttpErrorInfo handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return createHttpErrorInfo(HttpStatus.BAD_REQUEST, ex);
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = HttpErrorInfo.class)))
    public HttpErrorInfo handleGlobalException(Exception ex) {
        return createHttpErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, Exception ex) {
        final String path = serviceUtil.getServiceAddress();
        Map<String, Object> details = new HashMap<>();
        
        if (ex instanceof InvalidInputException) {
            String message = ex.getMessage();
            if (message.contains("ID no puede ser negativo")) {
                details.put("id", "El ID debe ser un número positivo");
            } else if (message.contains("nombre")) {
                details.put("name", message);
            } else if (message.contains("email")) {
                details.put("email", message);
            } else if (message.contains("contraseña")) {
                details.put("password", message);
            } else {
                details.put("error", message);
            }
        } else {
            details.put("error", ex.getMessage());
        }
        
        return new HttpErrorInfo(path, httpStatus, ex.getMessage(), details);
    }
}
