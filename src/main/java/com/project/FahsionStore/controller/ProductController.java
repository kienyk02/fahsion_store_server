package com.project.FahsionStore.controller;

import com.project.FahsionStore.model.Product;
import com.project.FahsionStore.service.ProductService;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
        product.getColors().forEach(color -> {
                    color.getSizes().forEach(size -> {
                        size.setColor(color);
                    });
                    color.setProduct(product);
                }
        );
        return ResponseEntity.ok().body(productService.saveProduct(product));
    }

    /////// PUT ////////
    @PostMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") int id,
            @RequestBody Product product
    ) {
        product.getColors().forEach(color -> {
                    color.getSizes().forEach(size -> {
                        size.setColor(color);
                    });
                    color.setProduct(product);
                }
        );
        return ResponseEntity.ok().body(productService.updateProduct(id, product));
    }

    /////// GET ///////
    @GetMapping("/products")
    public ResponseEntity<List<Product>> fetchAllProducts(
            @RequestParam(value = "available", defaultValue = "1") int isAvailable,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "0") int limit) {
        if (page != 0 && limit != 0) {
            Pageable pageable = PageRequest.of(page - 1, limit);
            return ResponseEntity.ok().body(productService.fetchAllProducts(isAvailable, pageable));
        } else {
            return ResponseEntity.ok().body(productService.fetchAllProducts(isAvailable));
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> fetchProductById(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.fetchProductById(id));
    }

    @GetMapping("/products/search/{keyword}")
    public ResponseEntity<?> searchProducts(
            @RequestParam(value = "available", defaultValue = "1") int isAvailable,
            @PathVariable("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return ResponseEntity.ok(productService.searchProducts(isAvailable, keyword, pageable));
    }

    @GetMapping("/products/filter")
    public ResponseEntity<?> getProductSearchWithFilter(
            @RequestParam(value = "available", defaultValue = "1") int isAvailable,
            @RequestParam("categoryIds") List<Integer> categoryIds,
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam("fromPrice") int fromPrice,
            @RequestParam("toPrice") int toPrice,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        if (Objects.equals(sort, "ASC")) {
            pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.ASC, "price"));
        } else if (Objects.equals(sort, "DESC")) {
            pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "price"));
        }
        return ResponseEntity.ok(productService.getProductSearchWithFilter(
                isAvailable,
                categoryIds,
                keyword,
                fromPrice,
                toPrice,
                sort,
                pageable));
    }

    @GetMapping("/products/sort/sell")
    public ResponseEntity<?> getProductsBestSeller(
            @RequestParam(value = "available", defaultValue = "1") int isAvailable,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return ResponseEntity.ok().body(productService.findProductsBestSeller(isAvailable, pageable));
    }

    @GetMapping("/products/sort/discount")
    public ResponseEntity<?> getProductsBestDiscount(
            @RequestParam(value = "available", defaultValue = "1") int isAvailable,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return ResponseEntity.ok().body(productService.findProductsBestDiscount(isAvailable, pageable));
    }

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(
            @RequestParam(value = "available", defaultValue = "1") int isAvailable,
            @PathVariable("categoryId") int id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "0") int limit) {
        if (page != 0 && limit != 0) {
            Pageable pageable = PageRequest.of(page - 1, limit);
            return ResponseEntity.ok().body(productService.getProductsByCategory(isAvailable, id, pageable));
        } else {
            return ResponseEntity.ok().body(productService.getProductsByCategory(isAvailable, id));
        }
    }

    /////// DELETE ///////
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }

}
