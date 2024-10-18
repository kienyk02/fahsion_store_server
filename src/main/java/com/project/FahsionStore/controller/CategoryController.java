package com.project.FahsionStore.controller;

import com.project.FahsionStore.model.Category;
import com.project.FahsionStore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PutMapping("/categories")
    public ResponseEntity<?> saveCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.saveCategory(category));
    }

    @GetMapping("/categories")
    public ResponseEntity<?> fetchAllCategory() {
        return ResponseEntity.ok(categoryService.fetchAllCategory());
    }

    @GetMapping("categories/list")
    public ResponseEntity<?> fetchCategoryList() {
        return ResponseEntity.ok(categoryService.fetchCategoryList());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> fetchCategoryById(@PathVariable int id) {
        return ResponseEntity.ok(categoryService.fetchCategoryById(id));
    }

    @DeleteMapping("categories/{id}")
    public String deleteCategoryById(@PathVariable int id) {
        return categoryService.deleteCategoryById(id);
    }

    @GetMapping("categories/sub/{id}")
    public ResponseEntity<?> findAllSubCategoryIds(@PathVariable int id) {
        return ResponseEntity.ok(categoryService.findAllSubCategoryIds(id));
    }

}
