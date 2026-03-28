package com.aurava.aurava_backend.service;


import com.aurava.aurava_backend.DTO.CartResponse;
import com.aurava.aurava_backend.DTO.CartRequest;

public interface CartService {

    CartResponse getCart();

    CartResponse addToCart(CartRequest request);

    CartResponse updateQuantity(Long cartItemId, Integer quantity);

    void removeItem(Long cartItemId);

    void clearCart();
}