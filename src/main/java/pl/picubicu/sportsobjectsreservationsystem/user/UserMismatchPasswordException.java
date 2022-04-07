package pl.picubicu.sportsobjectsreservationsystem.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserMismatchPasswordException extends RuntimeException {
    public UserMismatchPasswordException(String message) {
        super(message);
    }
}
