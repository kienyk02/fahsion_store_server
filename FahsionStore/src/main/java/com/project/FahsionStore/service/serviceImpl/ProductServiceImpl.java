package com.project.FahsionStore.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.FahsionStore.model.Product;
import com.project.FahsionStore.repository.CategoryRepository;
import com.project.FahsionStore.repository.ProductRepository;
import com.project.FahsionStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product saveProduct(String productString, MultipartFile image) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productString, Product.class);

        product.setImage(image.getBytes());

        return productRepository.save(product);
    }

    @Override
    public List<Product> fetchAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> fetchProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameLike(keyword);
    }

    @Override
    public String deleteProductById(int id) {
        productRepository.deleteById(id);
        return "Product with id " + id + " deleted successfully";
    }

    @Override
    public Product updateProduct(int id, String productString, MultipartFile image) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productString, Product.class);

        Product productDb = productRepository.findById(id).orElse(null);

        assert productDb != null;

        if (Objects.nonNull(product.getName()) &&
                !"".equalsIgnoreCase(product.getName())) {
            productDb.setName(product.getName());
        }

        if (Objects.nonNull(product.getDescription()) &&
                !"".equalsIgnoreCase(product.getDescription())) {
            productDb.setDescription(product.getDescription());
        }

        if (product.getPrice() != 0) {
            productDb.setPrice(product.getPrice());
        }

        if (productDb.getType() != product.getType()) {
            productDb.setType(product.getType());
        }

        if (Objects.nonNull(product.getCategories())) {
            productDb.setCategories(product.getCategories());
        }

        if (Objects.nonNull(image)) {
            productDb.setImage(image.getBytes());
        }

        return productRepository.save(productDb);
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
    public List<Product> getProductsByCategory(int categoryId) {
        List<Integer> categoryIds = categoryRepository.findAllSubCategoryIds(categoryId);

        return productRepository.findProductsByCategoryIds(categoryIds);
    }

}
