package com.example.productlast.dto;


import com.example.productlast.model.Category;
import com.example.productlast.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {

    private Long id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;


  public Product toProduct(){
        Product p = new Product();
        p.setId(id);
        p.setTitle(title);
        p.setDescription(description);
        p.setPrice(price);
        p.setImageUrl(image);

        Category cat = new Category();
        cat.setTitle(category);
        p.setCategory(cat);
        return p;

    }

}
