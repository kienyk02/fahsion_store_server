package com.project.FahsionStore.service.serviceImpl;

import com.project.FahsionStore.model.Product;
import com.project.FahsionStore.repository.CategoryRepository;
import com.project.FahsionStore.repository.ProductRepository;
import com.project.FahsionStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> fetchAllProducts(int isAvailable) {
        return productRepository.findAll(isAvailable);
    }

    @Override
    public List<Product> fetchAllProducts(int isAvailable, Pageable pageable) {
        return productRepository.findAllWithPage(isAvailable, pageable).getContent();
    }

    @Override
    public Optional<Product> fetchProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> searchProducts(int isAvailable, String keyword, Pageable pageable) {
        return productRepository.findProductSearch(isAvailable, keyword, pageable).getContent();
    }

    @Override
    public List<Product> findProductsBestSeller(int isAvailable, Pageable pageable) {
        return productRepository.findProductsBestSeller(isAvailable, pageable).getContent();
    }

    @Override
    public List<Product> findProductsBestDiscount(int isAvailable, Pageable pageable) {
        return productRepository.findProductsBestDiscount(isAvailable, pageable).getContent();
    }

    @Override
    public String deleteProductById(int id) {
        productRepository.deleteById(id);
        return "Product with id " + id + " deleted successfully";
    }

    @Override
    public Product updateProduct(int id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public List<Product> fetchProductsFilterByCategoriesAndPrice(List<Integer> categoryIds, int listCount, int price) {
        return productRepository.findProductsFilterByCategories(categoryIds, listCount, price);
    }

    @Override
    public List<Product> fetchProductsByTypeWithSize(int id, int quantity) {
        return productRepository.fetchProductsByTypeWithSize(id, quantity);
    }

    @Override
    public List<Product> getProductsByCategory(int isAvailable, int categoryId) {
        List<Integer> categoryIds = categoryRepository.findAllSubCategoryIds(categoryId);

        return productRepository.findProductsByCategoryIds(isAvailable, categoryIds);
    }

    @Override
    public List<Product> getProductsByCategory(int isAvailable, int categoryId, Pageable pageable) {
        List<Integer> categoryIds = categoryRepository.findAllSubCategoryIds(categoryId);

        return productRepository.findProductsByCategoryIds(isAvailable, categoryIds, pageable).getContent();
    }

}
