package com.project.FahsionStore.repository;

import com.project.FahsionStore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE (:isAvailable = 0 OR p.isAvailable = :isAvailable)")
    public List<Product> findAll(int isAvailable);

    @Query("SELECT p FROM Product p WHERE (:isAvailable = 0 OR p.isAvailable = :isAvailable)")
    public Page<Product> findAllWithPage(int isAvailable, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.tags t WHERE (:isAvailable = 0 OR p.isAvailable = :isAvailable) " +
            "AND (p.name LIKE %:keyword% " +
            "OR p.description LIKE %:keyword% " +
            "OR LOWER(t) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    public Page<Product> findProductSearch(int isAvailable, String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (:isAvailable = 0 OR p.isAvailable = :isAvailable) " +
            "ORDER BY p.sales DESC")
    public Page<Product> findProductsBestSeller(int isAvailable, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (:isAvailable = 0 OR p.isAvailable = :isAvailable) " +
            "ORDER BY p.discount DESC")
    public Page<Product> findProductsBestDiscount(int isAvailable, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c WHERE (:isAvailable = 0 OR p.isAvailable = :isAvailable) " +
            "AND c.id IN :categoryIds")
    List<Product> findProductsByCategoryIds(
            int isAvailable,
            List<Integer> categoryIds);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c WHERE (:isAvailable = 0 OR p.isAvailable = :isAvailable) " +
            "AND c.id IN :categoryIds")
    Page<Product> findProductsByCategoryIds(
            int isAvailable,
            List<Integer> categoryIds, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.tags t JOIN p.categories c WHERE (:isAvailable = 0 OR p.isAvailable = :isAvailable) " +
            "AND c.id IN :categoryIds " +
            "AND (p.name LIKE %:keyword% " +
            "OR p.description LIKE %:keyword% " +
            "OR LOWER(t) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND p.price >= :fromPrice AND p.price <= :toPrice")
    Page<Product> getProductSearchWithFilter(
            int isAvailable,
            List<Integer> categoryIds,
            String keyword,
            int fromPrice,
            int toPrice,
            Pageable pageable);

}
