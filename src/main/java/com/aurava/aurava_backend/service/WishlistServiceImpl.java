package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.DTO.WishlistItemResponse;
import com.aurava.aurava_backend.DTO.WishlistResponse;
import com.aurava.aurava_backend.entity.*;
import com.aurava.aurava_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private User getLoggedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Wishlist getOrCreateWishlist(User user) {
        return wishlistRepository.findByUser(user)
                .orElseGet(() -> wishlistRepository.save(
                        Wishlist.builder().user(user).build()
                ));
    }

    private WishlistResponse mapToResponse(Wishlist wishlist) {

    List<WishlistItemResponse> products = wishlist.getItems().stream()
        .map(item -> WishlistItemResponse.builder()
            .itemId(item.getId())
            .productId(item.getProduct().getId())
            .productName(item.getProduct().getName())
            .price(item.getProduct().getPrice())
            .imageUrl(item.getProduct().getImageUrl())
            .build())
        .toList();

    return WishlistResponse.builder()
        .wishlistId(wishlist.getId())
        .products(products)
        .build();
}

    @Override
    public WishlistResponse getWishlist() {
        Wishlist wishlist = getOrCreateWishlist(getLoggedUser());
        return mapToResponse(wishlist);
    }

    @Override
    public WishlistResponse addToWishlist(Long productId) {

        Wishlist wishlist = getOrCreateWishlist(getLoggedUser());

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        boolean exists = wishlistItemRepository
                .findByWishlistAndProduct(wishlist, product)
                .isPresent();

        if (exists) {
            throw new RuntimeException("Already in wishlist");
        }

        WishlistItem item = WishlistItem.builder()
                .wishlist(wishlist)
                .product(product)
                .build();

        wishlist.getItems().add(item);
        wishlistItemRepository.save(item);

        return mapToResponse(wishlist);
    }

    @Override
    public void removeFromWishlist(Long itemId) {

        WishlistItem item = wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getWishlist().getUser().getId().equals(getLoggedUser().getId())) {
            throw new RuntimeException("Unauthorized");
        }

        wishlistItemRepository.delete(item);
    }
}