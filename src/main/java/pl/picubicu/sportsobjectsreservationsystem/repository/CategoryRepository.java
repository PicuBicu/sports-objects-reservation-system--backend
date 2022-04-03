package pl.picubicu.sportsobjectsreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.picubicu.sportsobjectsreservationsystem.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    void deleteByName(String name);
}
