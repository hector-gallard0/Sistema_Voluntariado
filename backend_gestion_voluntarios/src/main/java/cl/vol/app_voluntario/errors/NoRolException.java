package cl.vol.app_voluntario.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoRolException extends RuntimeException {
    public NoRolException(String message) {
        super(message);
    }
}