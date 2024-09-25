package com.jambo.dream_shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jambo.dream_shop.dto.ProductDto;
import com.jambo.dream_shop.exceptions.ResourceNotFoundException;
import com.jambo.dream_shop.model.Product;
import com.jambo.dream_shop.request.AddProductRequest;
import com.jambo.dream_shop.request.ProductUpdateRequest;
import com.jambo.dream_shop.response.ApiResponse;
import com.jambo.dream_shop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;






@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    
    private  final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products=productService.getAllProducts();
        List<ProductDto> convertProducts=productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Success", convertProducts));
    }
    // @Transactional
    @GetMapping("product/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
       try {
        Product product=productService.getProductById(productId);
        ProductDto productDto=productService.convertProductDto(product);
           return ResponseEntity.ok(new ApiResponse("success", productDto));
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), productId));
    }
    }
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct=productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("added sucessful", theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    @PutMapping("product/{productId}")
    public ResponseEntity<ApiResponse> putMethodName(@PathVariable Long productId, @RequestBody ProductUpdateRequest productRequest) {
        try {
            Product theProduct=productService.updateProductById(productRequest,productId);
            return ResponseEntity.ok(new ApiResponse("updated success", theProduct));
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductId(productId);
            return ResponseEntity.ok(new ApiResponse("Deleted sccessfull", productId));
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("product/{brandName}/productName")
    public ResponseEntity<ApiResponse> getProductByBrandNameAndName(@PathVariable String brandName,@PathVariable String productName) {
        try {
            List<Product> products=productService.getProductsByBrand(brandName);
            List<ProductDto> convertProducts=productService.getConvertedProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", convertProducts));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    
    @GetMapping("product/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,@RequestParam String brand) {
        try {
            List<Product> products=productService.getProductsByCategoryAndBrand(category,brand);
            List<ProductDto> convertProducts=productService.getConvertedProducts(products);

            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", convertProducts));
            }
            return ResponseEntity.ok(new ApiResponse("success", convertProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    @GetMapping("product/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products=productService.getProductsByName(name);
            List<ProductDto> convertProducts=productService.getConvertedProducts(products);

            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", convertProducts));
            }
            return ResponseEntity.ok(new ApiResponse("success", convertProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

      
    @GetMapping("/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products=productService.getProductsByBrand(brand);
            List<ProductDto> convertProducts=productService.getConvertedProducts(products);

            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", convertProducts));
            }
            return ResponseEntity.ok(new ApiResponse("success", convertProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
    

         
    @GetMapping("product/category/by-category")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String category) {
        try {
            List<Product> products=productService.getProductsByCategory(category);
            List<ProductDto> convertProducts=productService.getConvertedProducts(products);

            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", convertProducts));
            }
            return ResponseEntity.ok(new ApiResponse("success", convertProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> getProductByCount(@RequestParam String category,@RequestParam String name) {
        try {
            var productCount=productService.countProductsByBrandAndName(category, name);
           
            return ResponseEntity.ok(new ApiResponse("success", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
