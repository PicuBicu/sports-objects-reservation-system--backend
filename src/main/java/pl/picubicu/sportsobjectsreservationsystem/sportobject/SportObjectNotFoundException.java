package pl.picubicu.sportsobjectsreservationsystem.sportobject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SportObjectNotFoundException extends RuntimeException {
    public SportObjectNotFoundException(String message) {
        super(message);
    }
}
