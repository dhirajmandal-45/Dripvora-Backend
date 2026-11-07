package dripvora.fashion.exception;
import lombok.Getter;
import java.time.LocalDateTime;
@Getter
public class ErrorResponse {
    private final boolean success=false;
    private final int status;
    private final String message;
    private final LocalDateTime timestamp=LocalDateTime.now();
    public ErrorResponse(int status,String message){this.status=status;this.message=message;}
}
