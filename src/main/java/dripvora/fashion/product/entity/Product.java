package dripvora.fashion.product.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 30)
    private String gender;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false, unique = true, length = 100)
    private String sku;

    @Column(length = 500)
    private String imageUrl;

    @Column(nullable = false, length = 100)
    private String fabric;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ==========================================
    // CONSTRUCTORS
    // ==========================================

    public Product() {
    }

    public Product(
            String name,
            String description,
            BigDecimal price,
            BigDecimal discountPercentage,
            String category,
            String brand,
            String gender,
            Integer stockQuantity,
            String sku,
            String imageUrl,
            String fabric,
            boolean active) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.category = category;
        this.brand = brand;
        this.gender = gender;
        this.stockQuantity = stockQuantity;
        this.sku = sku;
        this.imageUrl = imageUrl;
        this.fabric = fabric;
        this.active = active;
    }

    // ==========================================
    // JPA TIMESTAMPS
    // ==========================================

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {

        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public String getGender() {
        return gender;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public String getSku() {
        return sku;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFabric() {
        return fabric;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // ==========================================
    // SETTERS
    // ==========================================

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}