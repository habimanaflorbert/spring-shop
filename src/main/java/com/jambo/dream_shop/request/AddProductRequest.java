package com.jambo.dream_shop.request;

import java.math.BigDecimal;

import com.jambo.dream_shop.model.Category;

import lombok.Data;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    
}
