package cl.vol.app_voluntario.errors;

import cl.vol.app_voluntario.auth.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<AuthenticationResponse> handleExistingUserException(ExistingUserException e) {
        return ResponseEntity
                .badRequest()
                .body(AuthenticationResponse.builder()
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(NoRolException.class)
    public ResponseEntity<AuthenticationResponse> handleNoRolException(ExistingUserException e) {
        return ResponseEntity
                .badRequest()
                .body(AuthenticationResponse.builder()
                        .errorMessage(e.getMessage())
                        .build());
    }
}
