package com.jambo.dream_shop.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jambo.dream_shop.dto.ImageDto;
import com.jambo.dream_shop.model.Image;

public interface IImageservice {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
    
}
