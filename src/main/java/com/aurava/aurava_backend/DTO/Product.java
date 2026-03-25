package com.aurava.aurava_backend.DTO;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class Product {
     
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Image URL is required")
    @Pattern(
        regexp = "^(http|https)://.*$",
        message = "Invalid image URL"
    )
    private String imageUrl;
}