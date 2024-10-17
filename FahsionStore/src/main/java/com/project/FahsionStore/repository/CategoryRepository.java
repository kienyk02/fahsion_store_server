package com.project.FahsionStore.repository;

import com.project.FahsionStore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "WITH RECURSIVE CategoryHierarchy AS (" +
            "SELECT id, parent_id, category_name FROM Categories WHERE id = :id " +
            "UNION ALL " +
            "SELECT c.id, c.parent_id, c.category_name FROM Categories c " +
            "INNER JOIN CategoryHierarchy ch ON c.parent_id = ch.id) " +
            "SELECT id FROM CategoryHierarchy", nativeQuery = true)
    List<Integer> findAllSubCategoryIds(@Param("id") Integer id);

    @Query(value = "WITH RECURSIVE CategoryHierarchy AS (" +
            "SELECT id, parent_id, category_name FROM Categories WHERE id IN :ids " +
            "UNION ALL " +
            "SELECT c.id, c.parent_id, c.category_name FROM Categories c " +
            "INNER JOIN CategoryHierarchy ch ON c.parent_id = ch.id) " +
            "SELECT id FROM CategoryHierarchy", nativeQuery = true)
    List<Integer> findAllSubCategoryIdsList(@Param("ids") List<Integer> ids);

    @Query(value = "SELECT c FROM Category c WHERE c.parentCategory.id=:id")
    Set<Category> findDirectSubCategory(@Param("id") Integer id);

}
