package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.entity.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);

    List<Product> getAllProducts();

    Product getProduct(Long id);

    void deleteProduct(Long id);

    Product updateProduct(Product product);

    List<String> getAllCategories();
    
    List<Product> getProductsByCategory(String category);
}