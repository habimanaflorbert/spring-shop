package com.jambo.dream_shop.service.category;

import java.util.List;

import com.jambo.dream_shop.model.Category;


public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updCategory(Category category,Long id);
    void deleteCategoryById(Long id);
}
