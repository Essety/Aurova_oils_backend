package com.aurava.aurava_backend.controller;

import com.aurava.aurava_backend.service.CartService;

import jakarta.validation.Valid;

import com.aurava.aurava_backend.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartResponse getCart() {
        return cartService.getCart();
    }

    @PostMapping
    public CartResponse addToCart(@Valid @RequestBody CartRequest request) {
        return cartService.addToCart(request);
    }

    @PutMapping("/{id}")
    public CartResponse updateQuantity(@PathVariable Long id,
                                       @RequestParam Integer quantity) {
        return cartService.updateQuantity(id, quantity);
    }

    @DeleteMapping("/{id}")
    public void removeItem(@PathVariable Long id) {
        cartService.removeItem(id);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }
}