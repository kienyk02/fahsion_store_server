package com.project.FahsionStore.controller;

import com.project.FahsionStore.model.Product;
import com.project.FahsionStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    /////// POST ///////

    @PutMapping("/products")
    public ResponseEntity<?> SaveProduct(
            @RequestBody Product product
    ) {
        return ResponseEntity.ok().body(productService.saveProduct(product));
    }

    /////// PUT ////////
    @PostMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") int id,
            @RequestBody Product product
    ) {
        return ResponseEntity.ok().body(productService.updateProduct(id, product));
    }

    /////// GET ///////
    @GetMapping("/products")
    public ResponseEntity<List<Product>> fetchAllProducts() {
        return ResponseEntity.ok().body(productService.fetchAllProducts());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> fetchProductById(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.fetchProductById(id));
    }

    @GetMapping("/products/search/{keyword}")
    public ResponseEntity<?> searchProducts(@PathVariable("keyword") String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    @GetMapping("/products/category/{id}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(productService.getProductsByCategory(id));
    }

    /////// DELETE ///////
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }

}
