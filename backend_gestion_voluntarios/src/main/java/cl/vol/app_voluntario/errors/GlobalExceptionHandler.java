package cl.vol.app_voluntario.errors;

import cl.vol.app_voluntario.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
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
}
