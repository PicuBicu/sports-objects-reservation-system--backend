package pl.picubicu.sportsobjectsreservationsystem.user.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.picubicu.sportsobjectsreservationsystem.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "UserRole")
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}
