package pl.picubicu.sportsobjectsreservationsystem.category;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.picubicu.sportsobjectsreservationsystem.custom.CustomResponse;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RequestMapping("api/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public List<Category> getCategories() {
        return this.categoryService.getAllCategories();
    }

    @RolesAllowed(value = {"ADMIN"})
    @PostMapping()
    public CustomResponse addCategory(@RequestParam String categoryName) {
        return new CustomResponse(this.categoryService.addNewCategory(categoryName));
    }

    @RolesAllowed(value = {"ADMIN"})
    @DeleteMapping()
    public CustomResponse deleteCategory(@RequestParam String categoryName) {
        return new CustomResponse(this.categoryService.deleteCategory(categoryName));
    }
}
