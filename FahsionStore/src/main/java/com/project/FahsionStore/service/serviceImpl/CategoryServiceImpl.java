package com.project.FahsionStore.service.serviceImpl;

import com.project.FahsionStore.model.Category;
import com.project.FahsionStore.repository.CategoryRepository;
import com.project.FahsionStore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> fetchAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> fetchCategoryList() {
        List<Category> categories = new ArrayList<>();
        categories.addAll(categoryRepository.findDirectSubCategory(1));
        categories.addAll(categoryRepository.findDirectSubCategory(2));
        categories.forEach(category -> {
            category.setSubCategories(categoryRepository.findDirectSubCategory(category.getId()));
        });
        return categories;
    }

    @Override
    public Category fetchCategoryById(int id) {
        Category category = categoryRepository.findById(id).orElse(new Category());
        category.setSubCategories(categoryRepository.findDirectSubCategory(id));
        return category;
    }

    @Override
    public String deleteCategoryById(int id) {
        categoryRepository.deleteById(id);
        return "Category with id = " + id + " deleted successfully";
    }

    @Override
    public List<Integer> findAllSubCategoryIds(int id) {
        return categoryRepository.findAllSubCategoryIds(id);
    }

    public Set<Category> findDirectSubCategory(int id) {
        return categoryRepository.findDirectSubCategory(id);
    }

}
