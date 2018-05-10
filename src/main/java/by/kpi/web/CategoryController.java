package by.kpi.web;

import by.kpi.domain.Category;
import by.kpi.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Pyotr Kukharenka
 * @since 30.04.2018
 */

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public List<Category> getCategories() {
        return categoryService.findAll();
    }

    @PostMapping("/")
    public Category save(@RequestBody Category category) {
        return this.categoryService.save(category);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody Long id) {
        final Optional<Category> category = this.categoryService.findById(id);
        category.ifPresent(this.categoryService::delete);
    }
}
