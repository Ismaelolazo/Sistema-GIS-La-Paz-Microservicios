package Sistema_GIS_La_Paz_Microservicios.Usuario_service.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
