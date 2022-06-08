package pl.picubicu.sportsobjectsreservationsystem.reservation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.picubicu.sportsobjectsreservationsystem.custom.CustomResponse;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/reservation")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @PreAuthorize(value = "#reservationDto.email == authentication.name and hasRole('USER')")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping()
    public CustomResponse addReservation(@Valid @RequestBody ReservationDto reservationDto) {
        log.info("Create {}", reservationDto);
        reservationService.addReservation(reservationDto);
        log.info("New reservation for user {} on {} has been made", reservationDto.getEmail(), reservationDto.getReservationDate());
        return new CustomResponse("Reservation for " + reservationDto.getEmail() + " on " + reservationDto.getReservationDate());
    }

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('USER') and #status == 'REQUESTED' or #status == 'CANCELED' or #status == 'CONFIRMED'")
    @PutMapping("/{id}/status/{status}")
    public CustomResponse setReservationStatus(@PathVariable Long id, @PathVariable String status) {
        log.info("Changing reservation status for id {}", id);
        reservationService.setReservationStatus(id, status);
        log.info("Status of reservation with id {} has been set to {}", id, status);
        return new CustomResponse("New status of reservation with id " + id + " is " + status);
    }

    @RolesAllowed(value = {"ADMIN"})
    @GetMapping("/status/{statusName}")
    public List<ReservationResponseDto> getReservationsWithStatus(@PathVariable String statusName) {
        log.info("Fetch reservation with status {}", statusName);
        return reservationService.getReservationsByStatus(statusName);
    }

    @RolesAllowed(value = {"ADMIN"})
    @GetMapping("/status/")
    public List<ReservationStatus> getReservationStatuses() {
        log.info("Fetching reservation statuses");
        return reservationService.getReservationStatuses();
    }
}
