package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.DTO.OrderRequest;
import com.aurava.aurava_backend.DTO.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long orderId);
}