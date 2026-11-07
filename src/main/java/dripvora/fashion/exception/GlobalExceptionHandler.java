package dripvora.fashion.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException e){return ResponseEntity.status(404).body(new ErrorResponse(404,e.getMessage()));}
    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<ErrorResponse> bad(BadRequestException e){return ResponseEntity.badRequest().body(new ErrorResponse(400,e.getMessage()));}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException e){
        String msg=e.getBindingResult().getFieldErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(new ErrorResponse(400,msg));
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> general(Exception e){return ResponseEntity.status(500).body(new ErrorResponse(500,e.getMessage()==null?"Internal server error":e.getMessage()));}
}
