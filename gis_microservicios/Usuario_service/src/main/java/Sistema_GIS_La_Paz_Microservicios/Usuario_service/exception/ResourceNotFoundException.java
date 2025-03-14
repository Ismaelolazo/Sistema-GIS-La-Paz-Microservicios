package Sistema_GIS_La_Paz_Microservicios.Usuario_service.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
