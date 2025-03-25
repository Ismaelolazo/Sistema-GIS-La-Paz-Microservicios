package Sistema_GIS_La_Paz_Microservicios.Usuario_service.http;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;
import java.util.Map;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "HttpErrorInfo",
    title = "Error Information",
    description = "Standard error response model",
    example = "{\"timestamp\":\"2023-11-19T22:14:20.905Z\",\"path\":\"/Usuario/1\",\"status\":404,\"error\":\"Not Found\",\"message\":\"Usuario not found with id: 1\",\"details\":{\"error\":\"El recurso solicitado no existe\"}}"
)
public class HttpErrorInfo {

    @Schema(description = "Timestamp of the error", example = "2023-11-19T22:14:20.905Z", requiredMode = Schema.RequiredMode.REQUIRED)
    private final ZonedDateTime timestamp;
    
    @Schema(description = "Request path", example = "/Usuario/1", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String path;
    
    @Schema(description = "HTTP status code", example = "404", requiredMode = Schema.RequiredMode.REQUIRED)
    private final int status;
    
    @Schema(description = "HTTP status phrase", example = "Not Found", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String error;
    
    @Schema(description = "Error message", example = "User not found with id: 1", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String message;
    
    @Schema(description = "Additional error details")
    private Map<String, Object> details;

    public HttpErrorInfo(String path, HttpStatus httpStatus, String message) {
        this(path, httpStatus, message, null);
    }

    public HttpErrorInfo(String path, HttpStatus httpStatus, String message, Map<String, Object> details) {
        this.timestamp = ZonedDateTime.now();
        this.path = path;
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.details = details;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }
}
