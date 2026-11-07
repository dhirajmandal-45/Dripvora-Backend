package dripvora.fashion.payment.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import dripvora.fashion.payment.dto.PaymentRequest;
import dripvora.fashion.payment.entity.Payment;
import dripvora.fashion.payment.service.PaymentService;

@RestController
@RequestMapping("/dripvora/payment")
public class PaymentController {
    private final PaymentService s;

    public PaymentController(PaymentService s) {
        this.s = s;
    }

    @PostMapping
    public Payment pay(Authentication a, @Valid @RequestBody PaymentRequest r) {
        return s.create(a.getName(), r);
    }
}
