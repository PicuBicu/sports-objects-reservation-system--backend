package pl.picubicu.sportsobjectsreservationsystem.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.picubicu.sportsobjectsreservationsystem.sportobject.SportObject;
import pl.picubicu.sportsobjectsreservationsystem.user.User;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ReservationResponseDto {
    private Long id;
    private User user;
    private ReservationStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reservationDate;

    private Integer numOfUsers;
    private SportObject sportObject;

    public static ReservationResponseDto fromReservation(Reservation reservation) {
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .user(reservation.getUser())
                .status(reservation.getStatus())
                .creationDate(reservation.getCreationDate())
                .reservationDate(reservation.getReservationDate())
                .numOfUsers(reservation.getNumOfUsers())
                .sportObject(reservation.getSportObject())
                .build();
    }
}
