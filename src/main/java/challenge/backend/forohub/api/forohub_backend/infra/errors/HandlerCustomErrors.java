package challenge.backend.forohub.api.forohub_backend.infra.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerCustomErrors {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity customErrorArgumentNotValid(MethodArgumentNotValidException e){
        var errors = e.getFieldErrors().stream().map(DataValidationError::new).toList();

        return ResponseEntity.badRequest().body(errors);
    }

    private record DataValidationError(String field, String error){
        public DataValidationError(FieldError fieldError){
            this(fieldError.getField(), fieldError.getDefaultMessage())
;        }
    }
}
