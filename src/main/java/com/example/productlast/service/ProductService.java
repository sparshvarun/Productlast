package com.example.productlast.service;

import com.example.productlast.exceptions.ProductNotFoundException;
import com.example.productlast.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotFoundException;
    Page<Product> getAllProducts(int pageSize, int pageNumber);
    Product createProduct(Product product);
    void deleteProduct(Long productId) throws ProductNotFoundException;
    Product updateProduct(Long productId, Product product) throws ProductNotFoundException;
    List<Product> getProductsByCategory(String category);




}
