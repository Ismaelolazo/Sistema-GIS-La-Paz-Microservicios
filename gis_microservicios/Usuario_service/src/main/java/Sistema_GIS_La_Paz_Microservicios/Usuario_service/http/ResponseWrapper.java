package Sistema_GIS_La_Paz_Microservicios.Usuario_service.http;

import java.time.ZonedDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "ResponseWrapper",
    title = "Response Wrapper",
    description = "Wrapper for standardized API responses",
    example = "{\"timestamp\":\"2023-11-19T22:14:20.905Z\",\"path\":\"/Usuario/1\",\"status\":200,\"error\":\"OK\",\"data\":{\"id\":1,\"name\":\"John Doe\",\"email\":\"john@example.com\",\"password\":\"password123\"}}"
)
public class ResponseWrapper<T> {
    
    @Schema(description = "Timestamp of the response", example = "2023-11-19T22:14:20.905Z")
    private final ZonedDateTime timestamp;
    
    @Schema(description = "Request path", example = "/Usuario/1")
    private final String path;
    
    @Schema(description = "HTTP status code", example = "200")
    private final int status;
    
    @Schema(description = "HTTP status phrase", example = "OK")
    private final String error;
    
    @Schema(description = "Response data")
    private final T data;
    
    public ResponseWrapper(String path, int status, String error, T data) {
        this.timestamp = ZonedDateTime.now();
        this.path = path;
        this.status = status;
        this.error = error;
        this.data = data;
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
    
    public T getData() {
        return data;
    }
}
