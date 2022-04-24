package pl.picubicu.sportsobjectsreservationsystem.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ReservationAlreadyExists extends RuntimeException {
    public ReservationAlreadyExists(String message) {
        super(message);
    }
}
