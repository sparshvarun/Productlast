package com.example.productlast.controller;

import com.example.productlast.dto.ErrorDto;
import com.example.productlast.exceptions.ProductNotFoundException;
import com.example.productlast.model.Product;
import com.example.productlast.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService) {
        this.productService = productService;
    }



    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        Product postRequestResponse = productService.createProduct(product);
        return postRequestResponse;

        //productService.createProduct();

    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {

        Product currentProduct = productService.getSingleProduct(productId);
        ResponseEntity<Product> res = new ResponseEntity<>(
                currentProduct, HttpStatus.OK);
        return res;

        //return currentProduct;

    }

    @GetMapping("/products")
    public Page<Product> getAllProducts(@RequestParam("pageSize")int pageSize, @RequestParam("pageNumber")int pageNumber)
    {

        return productService.getAllProducts(pageSize, pageNumber);

    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long productId, @RequestBody Product product) throws ProductNotFoundException {
        Product updatedProduct = productService.updateProduct(productId, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping("/products/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable("category") String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }



    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException ex) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
}
