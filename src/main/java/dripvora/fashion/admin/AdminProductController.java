package dripvora.fashion.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import dripvora.fashion.product.entity.Product;
import dripvora.fashion.product.service.ProductService;

@RestController
@RequestMapping("/dripvora/admin/products")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminProductController {

        private final ProductService productService;
        private final ObjectMapper objectMapper;

        public AdminProductController(ProductService productService) {

                this.productService = productService;
                this.objectMapper = new ObjectMapper();
        }

        // ==========================================
        // CREATE PRODUCT
        // ==========================================

        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Product> create(
                        @RequestPart("product") String productJson,
                        @RequestPart("image") MultipartFile image) {

                try {

                        Product product = objectMapper.readValue(
                                        productJson,
                                        Product.class);

                        Product savedProduct = productService.createProduct(
                                        product,
                                        image);

                        return ResponseEntity
                                        .status(HttpStatus.CREATED)
                                        .body(savedProduct);

                } catch (Exception e) {

                        throw new RuntimeException(
                                        "Invalid product JSON: " + e.getMessage());
                }
        }

        // ==========================================
        // UPDATE PRODUCT
        // ==========================================

        @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Product> update(
                        @PathVariable Long id,
                        @RequestPart("product") String productJson,
                        @RequestPart(value = "image", required = false) MultipartFile image) {

                try {

                        Product product = objectMapper.readValue(
                                        productJson,
                                        Product.class);

                        Product updatedProduct = productService.updateProduct(
                                        id,
                                        product,
                                        image);

                        return ResponseEntity.ok(updatedProduct);

                } catch (Exception e) {

                        throw new RuntimeException(
                                        "Invalid product JSON: " + e.getMessage());
                }
        }

        // ==========================================
        // DELETE PRODUCT
        // ==========================================

        @DeleteMapping("/{id}")
        public ResponseEntity<String> delete(
                        @PathVariable Long id) {

                productService.deleteProduct(id);

                return ResponseEntity.ok(
                                "Product deleted successfully");
        }
}