package pl.picubicu.sportsobjectsreservationsystem.sportobject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportObjectRepository extends JpaRepository<SportObject, Long> {
}
