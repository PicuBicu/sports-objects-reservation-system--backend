package pl.picubicu.sportsobjectsreservationsystem.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.picubicu.sportsobjectsreservationsystem.custom.CustomResponse;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/reservation")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping()
    public CustomResponse addReservation(@Valid @RequestBody ReservationDto reservationDto) {
        log.info("Create {}", reservationDto);
        reservationService.addReservation(reservationDto);
        log.info("New reservation for user {} on {} has been made", reservationDto.getEmail(), reservationDto.getReservationDate());
        return new CustomResponse("Reservation for " + reservationDto.getEmail() + " on " + reservationDto.getReservationDate());
    }
}
