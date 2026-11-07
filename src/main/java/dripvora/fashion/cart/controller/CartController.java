package dripvora.fashion.cart.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import dripvora.fashion.cart.dto.CartItemRequest;
import dripvora.fashion.cart.entity.Cart;
import dripvora.fashion.cart.service.CartService;

@RestController
@RequestMapping("/dripvora/cart")
public class CartController {
    private final CartService s;

    public CartController(CartService s) {
        this.s = s;
    }

    @GetMapping
    public Cart get(Authentication a) {
        return s.get(a.getName());
    }

    @PostMapping("/items")
    public Cart add(Authentication a, @Valid @RequestBody CartItemRequest r) {
        return s.add(a.getName(), r);
    }

    @PutMapping("/items/{productId}")
    public Cart update(Authentication a, @PathVariable Long productId, @RequestParam int quantity) {
        return s.update(a.getName(), productId, quantity);
    }

    @DeleteMapping("/items/{productId}")
    public Cart remove(Authentication a, @PathVariable Long productId) {
        return s.remove(a.getName(), productId);
    }

    @DeleteMapping
    public void clear(Authentication a) {
        s.clear(a.getName());
    }
}
