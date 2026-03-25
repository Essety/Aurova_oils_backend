package com.aurava.aurava_backend.DTO;
import jakarta.validation.constraints.*;


import lombok.Data;

@Data
public class CartRequest {
    
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 10, message = "Max quantity allowed is 10")
    private Integer quantity;

}