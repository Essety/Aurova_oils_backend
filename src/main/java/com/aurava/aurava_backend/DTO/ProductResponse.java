package com.aurava.aurava_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {

    private Long productId;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
}