package com.example.productlast.repositories;

import com.example.productlast.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Category save(Category category);
    Category findByTitle(String title);
}
