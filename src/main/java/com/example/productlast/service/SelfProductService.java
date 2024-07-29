package com.example.productlast.service;

import com.example.productlast.exceptions.ProductNotFoundException;
import com.example.productlast.model.Category;
import com.example.productlast.model.Product;
import com.example.productlast.repositories.CategoryRepository;
import com.example.productlast.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService{


    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }



    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> p = productRepository.findById(productId);
        if(p.isPresent()){
            return p.get();

        }

        throw new ProductNotFoundException("Product not found");

    }

    @Override
    public Page<Product> getAllProducts(int pageSize, int pageNumber) {

     Page<Product> allProducts = productRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("price").ascending()));
     return allProducts;
    }

    @Override
    public Product createProduct(Product product) {
        // let s say we pass the category tile through postman ie we are not passing the cat id
        //thus spring boot cannot identify the foreign key
// so here the goal is to fetch the id of category from the category table via the title
        Category cat = categoryRepository.findByTitle(product.getCategory().getTitle());
//1
        if(cat ==null){

            //mo category with our title in the database
            //so create it ourselves the category
            Category newCat = new Category();
            newCat.setTitle(product.getCategory().getTitle());
           Category newRow = categoryRepository.save(newCat);

           //newrow will have the category id
            //so set the category to our product with whatever the new row we have created
           product.setCategory(newRow);
        }
//2
        else{
            // if database is returning the category id am using it from database
            //b/c we are using the hibernate to create the id
            product.setCategory(cat);
        }

        Product savedProduct = productRepository.save(product);
        return savedProduct;

    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {

    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductNotFoundException {
        return null;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return List.of();
    }
}
