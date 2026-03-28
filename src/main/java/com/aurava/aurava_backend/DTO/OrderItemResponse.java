package com.aurava.aurava_backend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {

    private Long orderItemId;
    private Long productId; 
    private String productName;
    private Double price;
    private Integer quantity;
    private Double total;
}