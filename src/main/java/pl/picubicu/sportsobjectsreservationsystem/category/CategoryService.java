package pl.picubicu.sportsobjectsreservationsystem.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    public String addNewCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        this.categoryRepository.save(category);
        return String.format("Successfully added new category %s", categoryName);
    }

    @Transactional
    public String deleteCategory(String categoryName) {
        this.categoryRepository.deleteByName(categoryName);
        return String.format("Successfully deleted category %s", categoryName);
    }
}
