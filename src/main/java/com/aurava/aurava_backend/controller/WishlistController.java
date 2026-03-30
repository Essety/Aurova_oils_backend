package com.aurava.aurava_backend.controller;

import com.aurava.aurava_backend.DTO.WishlistResponse;
import com.aurava.aurava_backend.service.WishlistService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@CrossOrigin
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public WishlistResponse getWishlist() {
        return wishlistService.getWishlist();
    }

    @PostMapping("/{productId}")
    public WishlistResponse addToWishlist(@Valid @PathVariable Long productId) {
        return wishlistService.addToWishlist(productId);
    }

    @DeleteMapping("/{itemId}")
    public void removeFromWishlist(@Valid @PathVariable Long itemId) {
        wishlistService.removeFromWishlist(itemId);
    }
     @DeleteMapping("/clear")
    public void clearWishlist() {
        wishlistService.clearWishlist();
    }
}