package pl.picubicu.sportsobjectsreservationsystem.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ReservationStatus")
@Table(name = "reservation_statuses", uniqueConstraints = {
        @UniqueConstraint(name = "status_name_unique_constraint",
                columnNames = {
                        "name"
                })
})
public class ReservationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public ReservationStatus(String statusName) {
        name = statusName;
    }
}
