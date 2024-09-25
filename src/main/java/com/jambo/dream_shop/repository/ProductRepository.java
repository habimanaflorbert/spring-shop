package com.jambo.dream_shop.repository;

import java.util.List;
import java.lang.String;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jambo.dream_shop.model.Category;
import com.jambo.dream_shop.model.Product;

public interface ProductRepository  extends JpaRepository<Product, Long>{

    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);
    

    List<Product> findByBrandAndName(String brand, String name);

    List<Product> findByName(String name);
    
    Long countByBrandAndName(String brand, String name);
    
   List<Product> findByCategoryAndBrand(Category category, String brand);

}
