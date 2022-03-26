package pl.picubicu.sportsobjectsreservationsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UserNotActivatedException extends RuntimeException {
    public UserNotActivatedException(String message) {
        super(message);
    }
}
