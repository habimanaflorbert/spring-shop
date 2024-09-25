package com.jambo.dream_shop.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jambo.dream_shop.dto.ImageDto;
import com.jambo.dream_shop.exceptions.ResourceNotFoundException;
import com.jambo.dream_shop.model.Image;
import com.jambo.dream_shop.model.Product;
import com.jambo.dream_shop.repository.ImageRepository;
import com.jambo.dream_shop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageservice {
    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id:" + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
        .ifPresentOrElse(imageRepository::delete,()-> {throw new ResourceNotFoundException("No image found with id:" + id);});
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {

       Product product=productService.getProductById(productId);

       List<ImageDto> savedImageDto=new ArrayList<>();
       for (MultipartFile file : files ){
       try{
       Image image=new Image();
       image.setFileName(file.getOriginalFilename());
       image.setFileType(file.getContentType());
       image.setImage(new SerialBlob(file.getBytes()));
       image.setProduct(product);
       
       String buildDownload="/api/v1/images/image/download/";
       String downloadUrl=buildDownload+image.getId();
       image.setDownloadUrl(downloadUrl);
       Image savedImage= imageRepository.save(image);
       
       savedImage.setDownloadUrl(downloadUrl);
       imageRepository.save(savedImage);

       ImageDto imageDto=new ImageDto();
       imageDto.setImageId(savedImage.getId());
       imageDto.setImageName(savedImage.getFileName());
       imageDto.setDownloadUrl(savedImage.getDownloadUrl());
       savedImageDto.add(imageDto);


    }catch(IOException | SQLException e){
        throw new RuntimeException(e.getMessage());
    }
       }
       return savedImageDto;
    }
    
    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image=getImageById(imageId);
        try{

            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }catch (IOException | SQLException e){
        throw new RuntimeException(e);
        }
    }



}
