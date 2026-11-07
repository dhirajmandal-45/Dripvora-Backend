package dripvora.fashion.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dripvora.fashion.product.entity.Product;
import dripvora.fashion.product.service.ProductService;

@RestController
@RequestMapping("/dripvora/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ==========================================
    // CREATE PRODUCT
    // ADMIN ONLY
    // ==========================================

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Product> createProduct(
            @RequestPart("product") Product product,
            @RequestPart("image") MultipartFile image) {

        Product savedProduct = productService.createProduct(
                product,
                image);

        return ResponseEntity.ok(savedProduct);
    }

    // ==========================================
    // GET PRODUCT BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.getProductById(id));
    }

    // ==========================================
    // GET ALL PRODUCTS
    // ==========================================

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {

        return ResponseEntity.ok(
                productService.getAllProducts());
    }

    // ==========================================
    // GET ACTIVE PRODUCTS
    // ==========================================

    @GetMapping("/active")
    public ResponseEntity<List<Product>> getActiveProducts() {

        return ResponseEntity.ok(
                productService.getActiveProducts());
    }

    // ==========================================
    // GET BY CATEGORY
    // ==========================================

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(
                productService.getByCategory(category));
    }

    // ==========================================
    // GET BY BRAND
    // ==========================================

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getByBrand(
            @PathVariable String brand) {

        return ResponseEntity.ok(
                productService.getByBrand(brand));
    }

    // ==========================================
    // GET BY GENDER
    // ==========================================

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<Product>> getByGender(
            @PathVariable String gender) {

        return ResponseEntity.ok(
                productService.getByGender(gender));
    }

    // ==========================================
    // UPDATE PRODUCT
    // ADMIN ONLY
    // ==========================================

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") Product product,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        Product updatedProduct = productService.updateProduct(
                id,
                product,
                image);

        return ResponseEntity.ok(updatedProduct);
    }

    // ==========================================
    // DELETE PRODUCT
    // ADMIN ONLY
    // ==========================================

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                "Product deleted successfully");
    }
}