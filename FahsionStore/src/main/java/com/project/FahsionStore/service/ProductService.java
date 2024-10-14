package com.project.FahsionStore.service;

import com.project.FahsionStore.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Product saveProduct(Product product);

    public List<Product> fetchAllProducts();

    public Optional<Product> fetchProductById(int id);

    public List<Product> searchProducts(String keyword);

    public String deleteProductById(int id);

    public Product updateProduct(int id, Product product);

    public List<Product> fetchProductsFilterByCategoriesAndPrice(List<Integer> categoryIds, int listCount, int price);

    public List<Product> fetchProductsByTypeWithSize(int id, int quantity);

    public List<Product> getProductsByCategory(int categoryId);
}
