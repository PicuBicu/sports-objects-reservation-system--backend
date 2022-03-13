package pl.picubicu.sportsobjectsreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.picubicu.sportsobjectsreservationsystem.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
