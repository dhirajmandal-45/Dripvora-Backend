package dripvora.fashion.review.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;
import dripvora.fashion.review.dto.ReviewRequest;
import dripvora.fashion.review.entity.Review;
import dripvora.fashion.review.repository.ReviewRepository;
import dripvora.fashion.user.repository.UserRepository;
import dripvora.fashion.product.repository.ProductRepository;
import dripvora.fashion.exception.*;

@RestController
@RequestMapping("/dripvora/reviews")
public class ReviewController {
    private final ReviewRepository reviews;
    private final UserRepository users;
    private final ProductRepository products;

    public ReviewController(ReviewRepository r, UserRepository u, ProductRepository p) {
        reviews = r;
        users = u;
        products = p;
    }

    @GetMapping("/product/{productId}")
    public List<Review> product(@PathVariable Long productId) {
        return reviews.findByProductId(productId);
    }

    @PostMapping("/{productId}")
    public Review add(Authentication a, @PathVariable Long productId, @Valid @RequestBody ReviewRequest r) {
        if (reviews.findByProductId(productId).stream().anyMatch(x -> x.getUser().getEmail().equals(a.getName())))
            throw new BadRequestException("You already reviewed this product");
        Review x = new Review();
        x.setUser(users.findByEmail(a.getName()).orElseThrow());
        x.setProduct(
                products.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
        x.setRating(r.getRating());
        x.setComment(r.getComment());
        return reviews.save(x);
    }
}
