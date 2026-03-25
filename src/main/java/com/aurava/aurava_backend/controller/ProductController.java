// package com.aurava.aurava_backend.controller;

// import com.aurava.aurava_backend.entity.Product;
// import com.aurava.aurava_backend.service.ProductService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/products")
// @RequiredArgsConstructor
// @CrossOrigin
// public class ProductController {

//     private final ProductService productService;

//     @GetMapping
//     public List<Product> getProducts(){
//         return productService.getAllProducts();
//     }
// }

// package com.aurava.aurava_backend.controller;

// import com.aurava.aurava_backend.entity.Product;
// import com.aurava.aurava_backend.service.ProductService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/products")
// @RequiredArgsConstructor
// @CrossOrigin
// public class ProductController {

//     private final ProductService productService;

//     @GetMapping
//     public List<Product> getAllProducts(){
//         return productService.getAllProducts();
//     }

//     @GetMapping("/{id}")
//     public Product getProduct(@PathVariable Long id){
//         return productService.getProduct(id);
//     }
// }

package com.aurava.aurava_backend.controller;

import com.aurava.aurava_backend.DTO.ProductResponse;
import com.aurava.aurava_backend.entity.Product;
import com.aurava.aurava_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAllProducts(){

        return productService.getAllProducts()
                .stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getImageUrl()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id){

        Product product = productService.getProduct(id);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getImageUrl()
        );
    }

    @GetMapping("/categories")
public List<String> getCategories() {
    return productService.getAllCategories();
}

    @GetMapping("/category/{category}")
public List<ProductResponse> getByCategory(@PathVariable String category) {

    return productService.getProductsByCategory(category)
            .stream()
            .map(product -> new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCategory(),
                    product.getImageUrl()
            ))
            .toList();
}

}