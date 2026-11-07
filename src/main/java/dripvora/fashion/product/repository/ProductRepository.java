package dripvora.fashion.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dripvora.fashion.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);

    List<Product> findByCategoryIgnoreCase(String category);

    List<Product> findByBrandIgnoreCase(String brand);

    List<Product> findByGenderIgnoreCase(String gender);

    List<Product> findByActiveTrue();

    List<Product> findByCategoryIgnoreCaseAndActiveTrue(String category);

    List<Product> findByBrandIgnoreCaseAndActiveTrue(String brand);

    List<Product> findByGenderIgnoreCaseAndActiveTrue(String gender);

    List<Product> findByStockQuantityLessThanEqual(Integer quantity);
}