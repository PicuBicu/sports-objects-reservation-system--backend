package pl.picubicu.sportsobjectsreservationsystem.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.picubicu.sportsobjectsreservationsystem.sportobject.SportObject;
import pl.picubicu.sportsobjectsreservationsystem.sportobject.SportObjectNotFoundException;
import pl.picubicu.sportsobjectsreservationsystem.sportobject.SportObjectRepository;
import pl.picubicu.sportsobjectsreservationsystem.user.User;
import pl.picubicu.sportsobjectsreservationsystem.user.UserNotFoundException;
import pl.picubicu.sportsobjectsreservationsystem.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ReservationStatusRepository statusRepository;
    private final SportObjectRepository sportObjectRepository;

    public void addReservation(ReservationDto reservationDto) {

        Optional<User> user = userRepository.findByEmail(reservationDto.getEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User " + reservationDto.getEmail() + " does not exists");
        }

        Optional<SportObject> sportObject = sportObjectRepository.findById(reservationDto.getSportObjectId());
        if (sportObject.isEmpty()) {
            throw new SportObjectNotFoundException("Sport object with id = " + reservationDto.getSportObjectId() + " does not exists");
        }

        Optional<Reservation> reservation = reservationRepository.findBySportObjectAndReservationDate(
                sportObject.get(),
                reservationDto.getReservationDate()
        );

        if (reservation.isPresent()) {
            if (isReservationBelongingToCurrentUser(reservation.get().getUser(), reservationDto.getEmail())) {
                throw new ReservationAlreadyExists("Reservation for "
                        + reservationDto.getEmail() + " on "
                        + reservationDto.getReservationDate()
                        + " has been already made");
            } else {
                throw new ReservationNotBelongingToUser("Reservation not belongs to user " + reservationDto.getEmail());
            }
        }

        Optional<ReservationStatus> status = statusRepository.findByName("REQUESTED");
        status.ifPresent(reservationStatus -> reservationRepository.save(Reservation.builder()
                .reservationDate(reservationDto.getReservationDate())
                .user(user.get())
                .status(reservationStatus)
                .numOfUsers(reservationDto.getNumOfUsers())
                .sportObject(sportObject.get())
                .build()
        ));
    }

    private boolean isReservationBelongingToCurrentUser(User user, String email) {
        return user.getEmail().equals(email);
    }

    public void setReservationStatus(Long id, String status) {
        Optional<Reservation> optReservation = reservationRepository.findById(id);
        optReservation.ifPresentOrElse(reservation -> {
            statusRepository.findByName(status).ifPresent(reservation::setStatus);
        }, () -> {
            throw new ReservationNotFound("There is no reservation with id " + id);
        });
    }

    public List<Reservation> getUserReservations(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return reservationRepository.findByUserEmail(email);
        } else {
            throw new UserNotFoundException("User with email " + email + " does not exist");
        }
    }
}
