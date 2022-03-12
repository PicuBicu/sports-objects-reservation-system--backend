package pl.picubicu.sportsobjectsreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.picubicu.sportsobjectsreservationsystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
