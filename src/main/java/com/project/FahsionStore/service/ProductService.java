package com.project.FahsionStore.service;

import com.project.FahsionStore.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Product saveProduct(Product product);

    public List<Product> fetchAllProducts(int isAvailable);

    public List<Product> fetchAllProducts(int isAvailable, Pageable pageable);

    public Optional<Product> fetchProductById(int id);

    public List<Product> searchProducts(int isAvailable, String keyword, Pageable pageable);

    public List<Product> findProductsBestSeller(int isAvailable, Pageable pageable);

    public List<Product> findProductsBestDiscount(int isAvailable, Pageable pageable);

    public String deleteProductById(int id);

    public Product updateProduct(int id, Product product);

    public List<Product> fetchProductsFilterByCategoriesAndPrice(List<Integer> categoryIds, int listCount, int price);

    public List<Product> fetchProductsByTypeWithSize(int id, int quantity);

    public List<Product> getProductsByCategory(int isAvailable, int categoryId);

    public List<Product> getProductsByCategory(int isAvailable, int categoryId, Pageable pageable);
}
