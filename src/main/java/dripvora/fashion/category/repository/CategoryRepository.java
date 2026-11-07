package dripvora.fashion.category.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import dripvora.fashion.category.entity.Category;
import java.util.*;
public interface CategoryRepository extends JpaRepository<Category,Long>{
 boolean existsByNameIgnoreCase(String name);
 Optional<Category> findByNameIgnoreCase(String name);
 List<Category> findByActiveTrue();
}
