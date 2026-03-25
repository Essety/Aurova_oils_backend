package com.aurava.aurava_backend.DTO;

import lombok.Data;
import lombok.Builder;


@Data
@Builder
public class CartItemResponse {

    private Long cartItemId;
    private Long productId; 
    private String productName;
    private Double price;
    private Integer quantity;
    private Double total;
}
