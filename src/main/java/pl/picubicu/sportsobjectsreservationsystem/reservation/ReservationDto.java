package pl.picubicu.sportsobjectsreservationsystem.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ReservationDto {

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Future(message = "Date is outdated")
    @NotNull(message = "Reservation date cannot be empty")
    private LocalDateTime reservationDate;

    @Min(message = "Minimum number of user is 1", value = 1)
    private Integer numOfUsers;

    @Min(message = "Sport object id is corrupted", value = 1)
    private Long sportObjectId;

}
