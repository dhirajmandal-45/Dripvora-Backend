package dripvora.fashion.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import dripvora.fashion.exception.ResourceNotFoundException;
import dripvora.fashion.globalConfig.CloudinaryService;
import dripvora.fashion.product.entity.Product;
import dripvora.fashion.product.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;

    public ProductService(
            ProductRepository productRepository,
            CloudinaryService cloudinaryService) {

        this.productRepository = productRepository;
        this.cloudinaryService = cloudinaryService;
    }

    // ==========================================
    // CREATE PRODUCT
    // ==========================================

    @Transactional
    public Product createProduct(
            Product product,
            MultipartFile image) {

        if (productRepository.existsBySku(product.getSku())) {

            throw new RuntimeException(
                    "Product with SKU already exists");
        }

        // Upload image to Cloudinary
        if (image != null && !image.isEmpty()) {

            String imageUrl = cloudinaryService.uploadImage(image);

            product.setImageUrl(imageUrl);
        }

        product.setActive(true);

        return productRepository.save(product);
    }

    // ==========================================
    // GET PRODUCT BY ID
    // ==========================================

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id));
    }

    // ==========================================
    // GET ALL PRODUCTS
    // ==========================================

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    // ==========================================
    // GET ACTIVE PRODUCTS
    // ==========================================

    @Transactional(readOnly = true)
    public List<Product> getActiveProducts() {

        return productRepository.findByActiveTrue();
    }

    // ==========================================
    // GET BY CATEGORY
    // ==========================================

    @Transactional(readOnly = true)
    public List<Product> getByCategory(String category) {

        return productRepository
                .findByCategoryIgnoreCaseAndActiveTrue(category);
    }

    // ==========================================
    // GET BY BRAND
    // ==========================================

    @Transactional(readOnly = true)
    public List<Product> getByBrand(String brand) {

        return productRepository
                .findByBrandIgnoreCaseAndActiveTrue(brand);
    }

    // ==========================================
    // GET BY GENDER
    // ==========================================

    @Transactional(readOnly = true)
    public List<Product> getByGender(String gender) {

        return productRepository
                .findByGenderIgnoreCaseAndActiveTrue(gender);
    }

    // ==========================================
    // UPDATE PRODUCT
    // ==========================================

    @Transactional
    public Product updateProduct(
            Long id,
            Product updatedProduct,
            MultipartFile image) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id));

        // ==========================================
        // SKU
        // ==========================================

        if (updatedProduct.getSku() != null
                && !updatedProduct.getSku().isBlank()
                && !updatedProduct.getSku()
                        .equals(product.getSku())) {

            if (productRepository.existsBySku(
                    updatedProduct.getSku())) {

                throw new RuntimeException(
                        "Product with SKU already exists");
            }

            product.setSku(
                    updatedProduct.getSku().trim());
        }

        // ==========================================
        // NAME
        // ==========================================

        if (updatedProduct.getName() != null
                && !updatedProduct.getName().isBlank()) {

            product.setName(
                    updatedProduct.getName().trim());
        }

        // ==========================================
        // DESCRIPTION
        // ==========================================

        if (updatedProduct.getDescription() != null) {

            product.setDescription(
                    updatedProduct.getDescription());
        }

        // ==========================================
        // PRICE
        // ==========================================

        if (updatedProduct.getPrice() != null) {

            product.setPrice(
                    updatedProduct.getPrice());
        }

        // ==========================================
        // DISCOUNT PERCENTAGE
        // ==========================================

        if (updatedProduct.getDiscountPercentage() != null) {

            product.setDiscountPercentage(
                    updatedProduct.getDiscountPercentage());
        }

        // ==========================================
        // CATEGORY
        // ==========================================

        if (updatedProduct.getCategory() != null
                && !updatedProduct.getCategory().isBlank()) {

            product.setCategory(
                    updatedProduct.getCategory().trim());
        }

        // ==========================================
        // BRAND
        // ==========================================

        if (updatedProduct.getBrand() != null
                && !updatedProduct.getBrand().isBlank()) {

            product.setBrand(
                    updatedProduct.getBrand().trim());
        }

        // ==========================================
        // GENDER
        // ==========================================

        if (updatedProduct.getGender() != null
                && !updatedProduct.getGender().isBlank()) {

            product.setGender(
                    updatedProduct.getGender().trim());
        }

        // ==========================================
        // FABRIC
        // ==========================================

        if (updatedProduct.getFabric() != null
                && !updatedProduct.getFabric().isBlank()) {

            product.setFabric(
                    updatedProduct.getFabric().trim());
        }

        // ==========================================
        // STOCK
        // ==========================================

        if (updatedProduct.getStockQuantity() != null) {

            product.setStockQuantity(
                    updatedProduct.getStockQuantity());
        }

        // ==========================================
        // IMAGE
        // ==========================================

        if (image != null && !image.isEmpty()) {

            String newImageUrl = cloudinaryService.uploadImage(image);

            product.setImageUrl(newImageUrl);
        }

        // ==========================================
        // ACTIVE
        // ==========================================

        if (updatedProduct.isActive() != product.isActive()) {

            product.setActive(
                    updatedProduct.isActive());
        }

        return productRepository.save(product);
    }

    // ==========================================
    // DELETE PRODUCT
    // ==========================================

    @Transactional
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id));

        productRepository.delete(product);
    }
}