package cl.vol.app_voluntario.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExistingUserException extends RuntimeException {
    public ExistingUserException(String message) {
        super(message);
    }
}