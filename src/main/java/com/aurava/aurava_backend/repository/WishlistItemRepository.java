package com.aurava.aurava_backend.repository;
import org.springframework.transaction.annotation.Transactional;

import com.aurava.aurava_backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    Optional<WishlistItem> findByWishlistAndProduct(Wishlist wishlist, Product product);

    @Modifying
    @Transactional
    void deleteByWishlist(Wishlist wishlist);
}