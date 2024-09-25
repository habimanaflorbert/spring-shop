package com.jambo.dream_shop.service.product;

import java.util.List;

import com.jambo.dream_shop.dto.ProductDto;
import com.jambo.dream_shop.model.Product;
import com.jambo.dream_shop.request.AddProductRequest;
import com.jambo.dream_shop.request.ProductUpdateRequest;

public interface IProductService {
    Product addProduct(AddProductRequest product);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    
    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

    Product getProductById(Long productId);

    Product updateProductById(ProductUpdateRequest request,Long id);

    void deleteProductId(Long id);
    ProductDto convertProductDto(Product product);
    List<ProductDto> getConvertedProducts(List<Product> products);

}