package com.aurava.aurava_backend.repository;

import com.aurava.aurava_backend.entity.Wishlist;
import com.aurava.aurava_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByUser(User user);
}