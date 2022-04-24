package pl.picubicu.sportsobjectsreservationsystem.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.picubicu.sportsobjectsreservationsystem.sportobject.SportObject;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findBySportObjectAndReservationDate(SportObject sportObject, LocalDateTime creationDate);
}
