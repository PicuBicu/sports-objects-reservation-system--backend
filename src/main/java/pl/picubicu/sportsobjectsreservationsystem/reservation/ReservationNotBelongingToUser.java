package pl.picubicu.sportsobjectsreservationsystem.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ReservationNotBelongingToUser extends RuntimeException {
    public ReservationNotBelongingToUser(String message) {
        super(message);
    }
}
