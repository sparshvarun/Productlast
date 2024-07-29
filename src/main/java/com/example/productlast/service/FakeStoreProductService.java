package com.example.productlast.service;

import com.example.productlast.dto.FakeStoreProductDto;
import com.example.productlast.exceptions.ProductNotFoundException;
import com.example.productlast.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {


    private RestTemplate restTemplate;

    private RedisTemplate redisTemplate;



    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {

        Product productFromRedis = (Product) redisTemplate.opsForHash().get("PRODUCT_",+productId);

        if(productFromRedis != null){
            return productFromRedis;
        }

        FakeStoreProductDto fakeStoreProductDto= restTemplate.getForObject("https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class);

        if(fakeStoreProductDto == null) {
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }

        redisTemplate.opsForHash().put("PRODUCT_", productId, fakeStoreProductDto.toProduct());
      return fakeStoreProductDto.toProduct();
    }

    @Override
    public Page<Product> getAllProducts(int pageSize, int pageNumber) {
        return null;

       // List<Product> products = new ArrayList<>();
       // FakeStoreProductDto[] res = restTemplate.getForObject(
         //       "https://fakestoreapi.com/products",
           //     FakeStoreProductDto[].class);

       // for(FakeStoreProductDto fs : res){

         //   products.add(fs.toProduct());
        //}

       // return products;

    }

    @Override
    public Product createProduct(Product product) {

        FakeStoreProductDto fs = new FakeStoreProductDto();

        fs.setId(product.getId());
        fs.setTitle(product.getTitle());
        fs.setDescription(product.getDescription());
        fs.setCategory(product.getCategory().getTitle());
        fs.setImage(product.getImageUrl());
        fs.setDescription(product.getDescription());
        fs.setPrice(product.getPrice());

        FakeStoreProductDto response = restTemplate.postForObject("https://fakestoreapi.com/products", fs,
                FakeStoreProductDto.class);
        return response.toProduct();

    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {

    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductNotFoundException {
        try {
            FakeStoreProductDto fs = new FakeStoreProductDto();
            fs.setId(product.getId());
            fs.setTitle(product.getTitle());
            fs.setDescription(product.getDescription());
            fs.setCategory(product.getCategory().getTitle());
            fs.setImage(product.getImageUrl());
            fs.setDescription(product.getDescription());
            fs.setPrice(product.getPrice());

            restTemplate.put("https://fakestoreapi.com/products/" + productId, fs);
            return product;
        } catch (Exception e) {
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        FakeStoreProductDto[] res = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + category,
                FakeStoreProductDto[].class);

        for (FakeStoreProductDto fs : res) {
            products.add(fs.toProduct());
        }

        return products;
    }





}
