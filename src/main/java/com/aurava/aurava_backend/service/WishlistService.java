package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.DTO.WishlistResponse;

public interface WishlistService {

    WishlistResponse getWishlist();

    WishlistResponse addToWishlist(Long productId);

    void removeFromWishlist(Long itemId);
}