package pl.picubicu.sportsobjectsreservationsystem.sportobject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.picubicu.sportsobjectsreservationsystem.category.Category;

import java.util.List;

@Repository
public interface SportObjectRepository extends JpaRepository<SportObject, Long> {
    List<SportObject> findSportObjectByCategoriesContaining(Category category);
}
