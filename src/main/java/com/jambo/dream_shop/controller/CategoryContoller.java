package com.jambo.dream_shop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jambo.dream_shop.exceptions.AlreadyExistsException;
import com.jambo.dream_shop.exceptions.ResourceNotFoundException;
import com.jambo.dream_shop.model.Category;
import com.jambo.dream_shop.response.ApiResponse;
import com.jambo.dream_shop.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryContoller {
    private final ICategoryService categoryService;
   

   @GetMapping("category/all")
   public ResponseEntity<ApiResponse> getAllCategories(){
    try {
        List<Category> categories=categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse("Found!", categories));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
    }
   
   }


   @PostMapping("category/add")
   public ResponseEntity<ApiResponse> addCatgeory(@RequestBody Category name) {
     try {
        Category thCategory=categoryService.addCategory(name);
         return ResponseEntity.ok(new ApiResponse("Success", thCategory));
    } catch (AlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
    }
   }

   @GetMapping("/category/{id}/")
   public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
       try {
        Category thCategory=categoryService.getCategoryById(id);
           return ResponseEntity.ok(new ApiResponse("Found",thCategory));
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
    }
   
   }


   @GetMapping("/category/{name}/")
   public ResponseEntity<ApiResponse> getCategoryName(@PathVariable String name) {
       try {
        Category thCategory=categoryService.getCategoryByName(name);
           return  ResponseEntity.ok(new ApiResponse("found", thCategory));
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
   }

   @DeleteMapping("/category/{id}/delete")
   public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
    try {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("found", null));
    } catch (ResourceNotFoundException e) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse (e.getMessage(),null));
    }
   }

   @PutMapping("/category/{id}/update")
   public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
      try {
        Category updCategory=categoryService.updCategory(category, id);
          return ResponseEntity.ok(new ApiResponse("Update success", updCategory));
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), category));
    }
   }
   


   
   
   
}
