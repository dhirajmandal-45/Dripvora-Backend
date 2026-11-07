package dripvora.fashion.admin;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import dripvora.fashion.category.dto.CategoryRequest;
import dripvora.fashion.category.entity.Category;
import dripvora.fashion.category.service.CategoryService;

@RestController
@RequestMapping("/dripora/admin/categories")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminCategoryController {
    private final CategoryService service;

    public AdminCategoryController(CategoryService s) {
        service = s;
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryRequest r) {
        return ResponseEntity.status(201).body(service.create(r));
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @Valid @RequestBody CategoryRequest r) {
        return service.update(id, r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
