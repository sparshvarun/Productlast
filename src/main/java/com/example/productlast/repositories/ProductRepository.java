package com.example.productlast.repositories;

import com.example.productlast.model.Product;
import com.example.productlast.repositories.projections.ProjectProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{

    Product save(Product product);

   Product findByTitle(String title);

   Product findByDescription(String description);

   Page<Product> findAll(Pageable pageable);



   //how to implement HQL
   @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
   List<Product> getProductsByCategoryId(@Param("categoryId")Long categoryId);



    //how to implement native query
    @Query(value = "SELECT * FROM Product p WHERE p.category_id = :categoryId", nativeQuery = true)
    List<Product> getProductsByCategoryIdWithNativeQueries(@Param("categoryId")Long categoryId);


    //HQL with projection
    //allows you to fetch certian coloumn from database
    //how to implement HQL

    @Query("SELECT p.title as title, p.id as id  FROM Product p WHERE p.category.id = :categoryId")
    List<ProjectProjection> getProductsByCategoryIdProjection(@Param("categoryId")Long categoryId);





}
