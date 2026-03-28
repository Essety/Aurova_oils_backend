package com.aurava.aurava_backend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class WishlistItemResponse {
    private Long itemId; 
    private Long productId;
    private String productName;
    private Double price;
    private String imageUrl;
}

