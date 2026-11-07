package dripvora.fashion.order.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.*;
import dripvora.fashion.order.dto.CheckoutRequest;
import dripvora.fashion.order.entity.Order;
import dripvora.fashion.order.service.OrderService;

@RestController
@RequestMapping("/dripvora/orders")
public class OrderController {
    private final OrderService s;

    public OrderController(OrderService s) {
        this.s = s;
    }

    @PostMapping("/checkout")
    public Order checkout(Authentication a, @Valid @RequestBody CheckoutRequest r) {
        return s.checkout(a.getName(), r);
    }

    @GetMapping
    public List<Order> mine(Authentication a) {
        return s.mine(a.getName());
    }

    @GetMapping("/{id}")
    public Order one(Authentication a, @PathVariable Long id) {
        return s.mineOne(a.getName(), id);
    }

    @GetMapping("/{id}/tracking")
    public String track(Authentication a, @PathVariable Long id) {
        return s.mineOne(a.getName(), id).getOrderStatus();
    }
}
