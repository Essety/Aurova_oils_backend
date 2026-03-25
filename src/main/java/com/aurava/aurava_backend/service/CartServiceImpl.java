package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.entity.Cart;
import com.aurava.aurava_backend.entity.CartItem;
import com.aurava.aurava_backend.entity.Product;
import com.aurava.aurava_backend.entity.User;
import com.aurava.aurava_backend.repository.*;
import com.aurava.aurava_backend.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private User getLoggedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder().user(user).build()
                ));
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }
    }

    private void calculateTotal(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }

    private CartResponse mapToResponse(Cart cart) {

        List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> CartItemResponse.builder()
                        .cartItemId(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .total(item.getPrice() * item.getQuantity())
                        .build()
                ).toList();

        return CartResponse.builder()
                .cartId(cart.getId())
                .items(items)
                .totalPrice(cart.getTotalPrice())
                .build();
    }

    @Override
    public CartResponse getCart() {
        User user = getLoggedUser();
        Cart cart = getOrCreateCart(user);
        return mapToResponse(cart);
    }

    @Override
    public CartResponse addToCart(CartRequest request) {

        validateQuantity(request.getQuantity());

        User user = getLoggedUser();
        Cart cart = getOrCreateCart(user);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Not enough stock");
        }

        CartItem item = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (item != null) {
            int newQuantity = item.getQuantity() + request.getQuantity();

        if (product.getStock() < newQuantity) {
       throw new RuntimeException("Not enough stock");
       }

              item.setQuantity(newQuantity);
        } else {
            item = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .price(product.getPrice())
                    .build();

            cart.getItems().add(item);
        }

        cartItemRepository.save(item);

        calculateTotal(cart);

        return mapToResponse(cart);
    }

    @Override
    public CartResponse updateQuantity(Long cartItemId, Integer quantity) {

        validateQuantity(quantity);

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getCart().getUser().getId().equals(getLoggedUser().getId())) {
            throw new RuntimeException("Unauthorized");
        }

        Product product = item.getProduct();

        if (product.getStock() < quantity) {
           throw new RuntimeException("Not enough stock");
         }

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        calculateTotal(item.getCart());

        return mapToResponse(item.getCart());
    }

    @Override
    public void removeItem(Long cartItemId) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getCart().getUser().getId().equals(getLoggedUser().getId())) {
            throw new RuntimeException("Unauthorized");
        }

        Cart cart = item.getCart();
        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        calculateTotal(cart);
    }

    @Override
    public void clearCart() {

        Cart cart = getOrCreateCart(getLoggedUser());

        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }
}