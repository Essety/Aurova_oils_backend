// package com.aurava.aurava_backend.service;

// import com.aurava.aurava_backend.DTO.*;
// import com.aurava.aurava_backend.entity.*;
// import com.aurava.aurava_backend.repository.*;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Service;

// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// @RequiredArgsConstructor
// public class OrderServiceImpl implements OrderService {

//     private final OrderRepository orderRepository;
//     private final CartRepository cartRepository;
//     private final UserRepository userRepository;

//     private User getLoggedUser() {
//         String email = SecurityContextHolder.getContext().getAuthentication().getName();

//         return userRepository.findByEmail(email)
//                 .orElseThrow(() -> new RuntimeException("User not found"));
//     }

//     private OrderResponse mapToResponse(Order order) {

//         List<OrderItemResponse> items = order.getItems().stream()
//                 .map(item -> OrderItemResponse.builder()
//                         .id(item.getId())
//                         .productName(item.getProduct().getName())
//                         .price(item.getPrice())
//                         .quantity(item.getQuantity())
//                         .total(item.getPrice() * item.getQuantity())
//                         .build()
//                 ).toList();

//         return OrderResponse.builder()
//                 .id(order.getId())
//                 .totalAmount(order.getTotalAmount())
//                 .status(order.getStatus())
//                 .createdAt(order.getCreatedAt())
//                 .items(items)
//                 .build();
//     }

//     @Override
//     public OrderResponse placeOrder(OrderRequest request) {

//         User user = getLoggedUser();

//         Cart cart = cartRepository.findByUser(user)
//                 .orElseThrow(() -> new RuntimeException("Cart is empty"));

//         if (cart.getItems().isEmpty()) {
//             throw new RuntimeException("Cart is empty");
//         }

//         Order order = Order.builder()
//                 .user(user)
//                 .totalAmount(cart.getTotalPrice())
//                 .status("PENDING")
//                 .createdAt(LocalDateTime.now())
//                 .build();

//         List<OrderItem> orderItems = cart.getItems().stream()
//                 .map(cartItem -> OrderItem.builder()
//                         .order(order)
//                         .product(cartItem.getProduct())
//                         .quantity(cartItem.getQuantity())
//                         .price(cartItem.getPrice())
//                         .build()
//                 ).toList();

//         order.setItems(orderItems);

//         Order savedOrder = orderRepository.save(order);

//         // Clear cart after order
//         cart.getItems().clear();
//         cart.setTotalPrice(0.0);
//         cartRepository.save(cart);

//         return mapToResponse(savedOrder);
//     }

//     @Override
//     public List<OrderResponse> getMyOrders() {

//         User user = getLoggedUser();

//         return orderRepository.findByUser(user)
//                 .stream()
//                 .map(this::mapToResponse)
//                 .toList();
//     }

//     @Override
//     public OrderResponse getOrderById(Long orderId) {

//         Order order = orderRepository.findById(orderId)
//                 .orElseThrow(() -> new RuntimeException("Order not found"));

//         if (!order.getUser().getId().equals(getLoggedUser().getId())) {
//             throw new RuntimeException("Unauthorized");
//         }

//         return mapToResponse(order);
//     }
// }

// package com.aurava.aurava_backend.service;

// import com.aurava.aurava_backend.DTO.*;
// import com.aurava.aurava_backend.entity.*;
// import com.aurava.aurava_backend.repository.*;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Service;

// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// @RequiredArgsConstructor
// public class OrderServiceImpl implements OrderService {

//     private final OrderRepository orderRepository;
//     private final CartRepository cartRepository;
//     private final UserRepository userRepository;
//     private final NotificationService notificationService;
//     private final ProductService productService;

//     private User getLoggedUser() {
//         String email = SecurityContextHolder.getContext().getAuthentication().getName();

//         return userRepository.findByEmail(email)
//                 .orElseThrow(() -> new RuntimeException("User not found"));
//     }

//     private OrderResponse mapToResponse(Order order) {

//         List<OrderItemResponse> items = order.getItems().stream()
//                 .map(item -> OrderItemResponse.builder()
//                         .orderItemId(item.getId())
//                         .productId(item.getProduct().getId())
//                         .productName(item.getProduct().getName())
//                         .price(item.getPrice())
//                         .quantity(item.getQuantity())
//                         .total(item.getPrice() * item.getQuantity())
//                         .build()
//                 ).toList();

//         return OrderResponse.builder()
//                 .orderId(order.getId())
//                 .totalAmount(order.getTotalAmount())
//                 .status(order.getStatus())
//                 .createdAt(order.getCreatedAt())
//                 .address(order.getAddress()) // ✅ FIXED
//                 .items(items)
//                 .build();
//     }

//     @Override
//     public OrderResponse placeOrder(OrderRequest request) {

//         if (request.getAddress() == null || request.getAddress().isEmpty()) {
//             throw new RuntimeException("Address is required");
//         }

//         User user = getLoggedUser();

//         Cart cart = cartRepository.findByUser(user)
//                 .orElseThrow(() -> new RuntimeException("Cart is empty"));

//         if (cart.getItems().isEmpty()) {
//             throw new RuntimeException("Cart is empty");
//         }

//         Order order = Order.builder()
//                 .user(user)
//                 .totalAmount(cart.getTotalPrice())
//                 .status("PENDING")
//                 .createdAt(LocalDateTime.now())
//                 .address(request.getAddress()) // ✅ FIXED
//                 .build();

//         List<OrderItem> orderItems = cart.getItems().stream()
//                 .map(cartItem -> OrderItem.builder()
//                         .order(order)
//                         .product(cartItem.getProduct())
//                         .quantity(cartItem.getQuantity())
//                         .price(cartItem.getPrice())
//                         .build()
//                 ).toList();

//         order.setItems(orderItems);

//         Order savedOrder = orderRepository.save(order);

//         // Clear cart
//         cart.getItems().clear();
//         cart.setTotalPrice(0.0);
//         cartRepository.save(cart);

//         notificationService.createNotification(
//     "Your order #" + savedOrder.getId() + " has been placed successfully 🎉"
// );

//         return mapToResponse(savedOrder);
        
//     }

//     @Override
//     public List<OrderResponse> getMyOrders() {

//         User user = getLoggedUser();

//         return orderRepository.findByUser(user)
//                 .stream()
//                 .map(this::mapToResponse)
//                 .toList();
//     }

//     @Override
//     public OrderResponse getOrderById(Long orderId) {

//         Order order = orderRepository.findById(orderId)
//                 .orElseThrow(() -> new RuntimeException("Order not found"));

//         if (!order.getUser().getId().equals(getLoggedUser().getId())) {
//             throw new RuntimeException("Unauthorized");
//         }

//         return mapToResponse(order);
//     }
// }

package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.DTO.*;
import com.aurava.aurava_backend.entity.Product;
import com.aurava.aurava_backend.entity.Order;
import com.aurava.aurava_backend.entity.Cart;
import com.aurava.aurava_backend.entity.CartItem;
import com.aurava.aurava_backend.entity.OrderItem;
import com.aurava.aurava_backend.entity.User;
import com.aurava.aurava_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final ProductRepository productRepository; // ✅ added

    // Get currently logged-in user
    private User getLoggedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Map Order entity to OrderResponse DTO
    private OrderResponse mapToResponse(Order order) {

        List<OrderItemResponse> items = order.getItems() == null ? List.of() :
                order.getItems().stream()
                .filter(item -> item != null && item.getProduct() != null)
                .map(item -> OrderItemResponse.builder()
                        .orderItemId(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .price(item.getPrice() != null ? item.getPrice() : 0.0)
                        .quantity(item.getQuantity() != null ? item.getQuantity() : 0)
                        .total(
                                (item.getPrice() != null ? item.getPrice() : 0.0) *
                                (item.getQuantity() != null ? item.getQuantity() : 0)
                        )
                        .build()
                ).toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount() != null ? order.getTotalAmount() : 0.0)
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .address(order.getAddress())
                .items(items)
                .build();
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {

        // ✅ Fix 1: Safe address validation
        if (request == null || request.getAddress() == null || request.getAddress().trim().isEmpty()) {
            throw new RuntimeException("Address is required");
        }

        User user = getLoggedUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // ✅ Fix 2: Safe stock handling
        for (CartItem cartItem : cart.getItems()) {

            if (cartItem == null || cartItem.getProduct() == null) {
                throw new RuntimeException("Invalid cart item");
            }

            Product product = cartItem.getProduct();

            int quantity = cartItem.getQuantity() != null ? cartItem.getQuantity() : 0;

            if (quantity <= 0) {
                throw new RuntimeException("Invalid quantity for product: " + product.getName());
            }

            Integer stock = product.getStock();

            if (stock == null) {
                throw new RuntimeException("Stock not available for product: " + product.getName());
            }

            if (stock < quantity) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            // ✅ subtract stock
            product.setStock(stock - quantity);

            // ✅ ensure persistence (safe)
            productRepository.save(product);
        }

        // ✅ Fix 3: Safe total amount
        double totalAmount = cart.getTotalPrice() != null ? cart.getTotalPrice() : 0.0;

        // Create Order
        Order order = Order.builder()
                .user(user)
                .totalAmount(totalAmount)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .address(request.getAddress())
                .build();

        // ✅ Fix 4: Safe mapping
        List<OrderItem> orderItems = cart.getItems().stream()
                .filter(item -> item != null && item.getProduct() != null)
                .map(cartItem -> OrderItem.builder()
                        .order(order)
                        .product(cartItem.getProduct())
                        .quantity(cartItem.getQuantity() != null ? cartItem.getQuantity() : 0)
                        .price(cartItem.getPrice() != null ? cartItem.getPrice() : 0.0)
                        .build()
                ).toList();

        order.setItems(orderItems);

        // Save order
        Order savedOrder = orderRepository.save(order);

        // ✅ Fix 5: Safe cart clear
        if (cart.getItems() != null) {
            cart.getItems().clear();
        }
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        // Send notification
        notificationService.createNotification(
                "Your order #" + savedOrder.getId() + " has been placed successfully 🎉"
        );

        return mapToResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getMyOrders() {

        User user = getLoggedUser();

        return orderRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getUser() == null || !order.getUser().getId().equals(getLoggedUser().getId())) {
            throw new RuntimeException("Unauthorized");
        }

        return mapToResponse(order);
    }
}