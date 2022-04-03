package pl.picubicu.sportsobjectsreservationsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.picubicu.sportsobjectsreservationsystem.model.Category;
import pl.picubicu.sportsobjectsreservationsystem.service.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public List<Category> getCategories() {
        return this.categoryService.getAllCategories();
    }

    @PostMapping()
    public String addCategory(@RequestParam String categoryName) {
        return this.categoryService.addNewCategory(categoryName);
    }

    @DeleteMapping()
    public String deleteCategory(@RequestParam String categoryName) {
        return this.categoryService.deleteCategory(categoryName);
    }
}
