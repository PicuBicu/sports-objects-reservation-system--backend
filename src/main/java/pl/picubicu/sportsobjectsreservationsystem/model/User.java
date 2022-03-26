package pl.picubicu.sportsobjectsreservationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    // TODO: timestamp?
    private Instant creationDate;
    // TODO: timestamp / loginDate?
    private Instant lastLoginDate;
    // TODO: maybe another table?
    private String role;
    private String phoneNumber;
    private Boolean isActivated;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    // TODO: one to many
    @OneToOne
    private Address address;
}
