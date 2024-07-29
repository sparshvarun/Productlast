package com.example.productlast;

import com.example.productlast.model.Category;
import com.example.productlast.model.Product;
import com.example.productlast.repositories.CategoryRepository;
import com.example.productlast.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductlastApplicationTests {


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testingQueries() {
        //List<Product> products = productRepository.getProductsByCategoryId(1L);\
        List<Product> products = productRepository.getProductsByCategoryIdWithNativeQueries(1L);
        System.out.println(products.get(0));
    }


    @Test
    void fetchCategory(){
        Category category = categoryRepository.findById(1L).get();
        System.out.println(category.getId());
        System.out.println("we are done");

        List<Product> currentProducts = category.getProducts();
        //It is going to execute a new query to fetch list of products

        System.out.println("Products fetched");

    }

}
