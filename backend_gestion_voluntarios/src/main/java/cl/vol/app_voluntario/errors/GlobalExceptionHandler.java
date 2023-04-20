package cl.vol.app_voluntario.errors;

import cl.vol.app_voluntario.response.AuthenticationResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<AuthenticationResponse> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity
                .badRequest()
                .body(AuthenticationResponse.builder()
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(QueryException.class)
    public ResponseEntity<?> handleQueryException(QueryException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InstitucionNotFoundException.class)
    public ResponseEntity<?> handleInstitucionNotFoundException(InstitucionNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDatesException.class)
    public ResponseEntity<?> handleInvalidDatesException(InvalidDatesException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
