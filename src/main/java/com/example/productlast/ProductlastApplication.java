package com.example.productlast;

import com.example.productlast.model.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductlastApplication {

    public static void main(String[] args) {

        Product p =new Product();




        SpringApplication.run(ProductlastApplication.class, args);
    }



}
