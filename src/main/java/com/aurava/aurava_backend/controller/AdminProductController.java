package com.aurava.aurava_backend.controller;

import com.aurava.aurava_backend.entity.Product;
import com.aurava.aurava_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
// import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminProductController {

    private final ProductService productService;

    @Value("${file.upload-dir}")
    private String uploadDir;
    
    @PostMapping(value="/products", consumes = "multipart/form-data")
public Product addProduct(
        @RequestParam String name,
        @RequestParam String description,
        @RequestParam Double price,
        @RequestParam String category,
        @RequestParam Integer stock,
        @RequestParam MultipartFile image
) {

    try {

        if (image == null || image.isEmpty()) {
            throw new RuntimeException("Image is null or empty");
        }

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        File dest = new File(uploadDir + "/" + fileName);

        image.transferTo(dest);

        String imageUrl = "/uploads/" + fileName;

        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .category(category)
                .stock(stock)
                .imageUrl(imageUrl)
                .build();

        return productService.addProduct(product);

    } catch (Exception e) {
        e.printStackTrace(); // 🔥 VERY IMPORTANT
        throw new RuntimeException("Error while adding product: " + e.getMessage());
    }
}
}
