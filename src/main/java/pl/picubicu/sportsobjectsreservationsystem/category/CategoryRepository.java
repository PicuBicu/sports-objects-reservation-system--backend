package pl.picubicu.sportsobjectsreservationsystem.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    void deleteByName(String name);
    List<Category> findCategoryByNameIn(Collection<String> categoriesNames);
}
