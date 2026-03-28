package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.entity.Product;
import com.aurava.aurava_backend.repository.CategoryProjection;
import com.aurava.aurava_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
public Product updateProduct(Product product) {
    return productRepository.save(product);
}

 @Override
public List<String> getAllCategories() {
    return productRepository.findDistinctBy()
            .stream()
            .map(CategoryProjection::getCategory)
            .filter(cat -> cat != null)
            .map(String::trim)
            .map(String::toLowerCase)
            .map(cat -> cat.replaceAll("\\s*-\\s*", "-"))  // remove spaces around hyphens
            .distinct()
            .toList();
}

    @Override
public List<Product> getProductsByCategory(String category) {

    String normalizedCategory = category
            .trim()
            .toLowerCase()
            .replaceAll("\\s*-\\s*", "-");

    return productRepository.findAll()
            .stream()
            .filter(product -> {
                if (product.getCategory() == null) return false;

                String dbCategory = product.getCategory()
                        .trim()
                        .toLowerCase()
                        .replaceAll("\\s*-\\s*", "-");

                return dbCategory.equals(normalizedCategory);
            })
            .toList();
}

}