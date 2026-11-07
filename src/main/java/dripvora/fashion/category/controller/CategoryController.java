package dripvora.fashion.category.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import dripvora.fashion.category.entity.Category;
import dripvora.fashion.category.service.CategoryService;

@RestController
@RequestMapping("/dripvora/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService s) {
        service = s;
    }

    @GetMapping
    public List<Category> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Category get(@PathVariable Long id) {
        return service.get(id);
    }
}
