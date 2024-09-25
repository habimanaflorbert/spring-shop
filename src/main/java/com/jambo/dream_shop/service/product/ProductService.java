package com.jambo.dream_shop.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.jambo.dream_shop.model.Category;
import com.jambo.dream_shop.model.Image;
import com.jambo.dream_shop.model.Product;
import com.jambo.dream_shop.dto.ImageDto;
import com.jambo.dream_shop.dto.ProductDto;
import com.jambo.dream_shop.exceptions.ProductNotFoundException;
import com.jambo.dream_shop.repository.CategoryRepository;
import com.jambo.dream_shop.repository.ImageRepository;
import com.jambo.dream_shop.repository.ProductRepository;
import com.jambo.dream_shop.request.AddProductRequest;
import com.jambo.dream_shop.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    
    
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }
    
    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }
    
    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }
    
    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        // return productRepository.findByCategoryAndBrand(category,brand);
        return productRepository.findByBrand(brand);
    }
    
    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }
    
    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
    
    
    @Override
    public void deleteProductId(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,()-> {new ProductNotFoundException("Product not Found!");});
    }
    @Override
    public  Product  addProduct(AddProductRequest request) {
      
        Category category=Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
        .orElseGet(()->{
            Category newCategory=new Category(request.getCategory().getName());
        return categoryRepository.save(newCategory);
        });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }
    
    private Product createProduct(AddProductRequest request,Category category){
        return new Product(
            request.getName(),
            request.getBrand(),
            request.getPrice(),
            request.getInventory(),
            request.getDescription(),
            category
        );
    }
    
    @Override
    public Product updateProductById(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
        .map(existingProduct -> updateExistingProduct(existingProduct, request))
        .map(productRepository::save)
        .orElseThrow(()-> new ProductNotFoundException("product not found !"));
    }
    
    private Product updateExistingProduct(Product existingProduct,ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        Category category=categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }
    
    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertProductDto).toList();
    }
    
   
    @Override
    public ProductDto convertProductDto(Product product){
        ProductDto productsDto=modelMapper.map(product, ProductDto.class);
        List<Image> images=imageRepository.findByProductId(product.getId());
        List<ImageDto> imagesDtos=images.stream()
        .map(image-> modelMapper.map(image,ImageDto.class)).toList();
        productsDto.setImages(imagesDtos);
        return productsDto;
      }

  
}
