package pl.picubicu.sportsobjectsreservationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.picubicu.sportsobjectsreservationsystem.dto.RegistrationRequestDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "email_unique_constraint",
                        columnNames = {
                                "email"
                        })
        })
public class User {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    private String email;
    private String password;
    private Instant creationDate;
    private String role;
    private Boolean isActivated;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    @OneToOne
    private Address address;

    public static User fromRegistrationDtoWithRole(RegistrationRequestDto request, String role) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .isActivated(false)
                .role(role)
                .build();
    }
}
