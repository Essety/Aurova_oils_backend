package com.aurava.aurava_backend.controller;

import com.aurava.aurava_backend.DTO.OrderRequest;
import com.aurava.aurava_backend.DTO.OrderResponse;
import com.aurava.aurava_backend.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
public OrderResponse placeOrder(@Valid @RequestBody OrderRequest request) {
    return orderService.placeOrder(request);
}

    @GetMapping
    public List<OrderResponse> getMyOrders() {
        return orderService.getMyOrders();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}