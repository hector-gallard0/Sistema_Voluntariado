package cl.vol.app_voluntario.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InstitucionNotFoundException extends RuntimeException {
    public InstitucionNotFoundException(String message) {
        super(message);
    }
}