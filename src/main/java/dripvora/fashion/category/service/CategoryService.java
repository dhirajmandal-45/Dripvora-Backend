package dripvora.fashion.category.service;
import org.springframework.stereotype.Service;
import dripvora.fashion.category.entity.Category;
import dripvora.fashion.category.dto.CategoryRequest;
import dripvora.fashion.category.repository.CategoryRepository;
import dripvora.fashion.exception.*;
import java.util.List;
@Service
public class CategoryService{
 private final CategoryRepository repo;
 public CategoryService(CategoryRepository repo){this.repo=repo;}
 public List<Category> all(){return repo.findByActiveTrue();}
 public Category get(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found"));}
 public Category create(CategoryRequest r){if(repo.existsByNameIgnoreCase(r.getName().trim()))throw new BadRequestException("Category already exists"); Category c=new Category();c.setName(r.getName().trim());c.setDescription(r.getDescription());return repo.save(c);}
 public Category update(Long id,CategoryRequest r){Category c=get(id);c.setName(r.getName().trim());c.setDescription(r.getDescription());return repo.save(c);}
 public void delete(Long id){Category c=get(id);c.setActive(false);repo.save(c);}
}
