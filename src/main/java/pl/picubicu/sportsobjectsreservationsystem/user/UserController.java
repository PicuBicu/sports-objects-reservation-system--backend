package pl.picubicu.sportsobjectsreservationsystem.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.picubicu.sportsobjectsreservationsystem.reservation.Reservation;
import pl.picubicu.sportsobjectsreservationsystem.reservation.ReservationService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static pl.picubicu.sportsobjectsreservationsystem.custom.SystemMessage.USER_DELETED;

@SecurityRequirement(name = "bearerAuth")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/user")
@RestController
@RolesAllowed(value = "ADMIN")
public class UserController {

    private final UserService userService;
    private final ReservationService reservationService;

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('USER') and #email == authentication.name")
    @GetMapping("/{email}")
    public UserResponseDto getUserByEmail(@PathVariable String email) {
        return this.userService.getUserByEmail(email);
    }

    @GetMapping("/")
    public List<UserResponseDto> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/activated/{isActivated}")
    public List<UserResponseDto> getAllUserWithStatus(@PathVariable("isActivated") Boolean isActivated) {
        return this.userService.getAllUserWithStatus(isActivated);
    }

    @DeleteMapping("{email}")
    public String deleteUser(@PathVariable("email") String email) {
        log.info(USER_DELETED);
        return this.userService.deleteUserByEmail(email);
    }

//    todo: change to custom response
    @PutMapping("/")
    public String changeUserStatus(@RequestParam("email") String email,
                                   @RequestParam("isActivated") Boolean isActivated) {
        log.info("Attempt to change activation status of user {}", email);
        return this.userService.changeUserStatusByEmail(email, isActivated);
    }

    @PreAuthorize(value = "#email == authentication.name and hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{email}/reservations")
    public List<Reservation> getUserReservations(@PathVariable String email) {
        log.info("Fetch reservation for {}", email);
        return reservationService.getUserReservations(email);
    }
}
