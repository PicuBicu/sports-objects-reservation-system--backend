package pl.picubicu.sportsobjectsreservationsystem.category;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CategoryInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        createCategory();
    }

    private void createCategory() {
        List<String> categoriesNames = List.of("orlik", "basen", "skatepark");
        if (categoryRepository.count() == 0) {
            for (String categoryName: categoriesNames) {
                Category category = new Category();
                category.setName(categoryName);
                categoryRepository.save(category);
            }
        }
    }
}
