package com.aurava.aurava_backend.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    private Long orderId;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private String address;
    private List<OrderItemResponse> items;
}