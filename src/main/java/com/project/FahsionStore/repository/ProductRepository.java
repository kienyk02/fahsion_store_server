package com.project.FahsionStore.repository;

import com.project.FahsionStore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
    public List<Product> findByNameLike(String keyword);

    @Query("SELECT p FROM Product p " +
            "JOIN p.categories c " +
            "WHERE c.id IN ?1 " +
            "GROUP BY p " +
            "HAVING COUNT(DISTINCT c) = ?2 AND p.price < ?3 ")
    public List<Product> findProductsFilterByCategories(List<Integer> categoryIds, int listCount, int price);

    @Query(
            value = "SELECT * from product p WHERE p.type = ?1 LIMIT ?2",
            nativeQuery = true
    )
    public List<Product> fetchProductsByTypeWithSize(int id, int quantity);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id IN :categoryIds")
    List<Product> findProductsByCategoryIds(@Param("categoryIds") List<Integer> categoryIds);

}
