package pl.picubicu.sportsobjectsreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.picubicu.sportsobjectsreservationsystem.model.SportObject;

@Repository
public interface SportObjectRepository extends JpaRepository<SportObject, Long> {
}
