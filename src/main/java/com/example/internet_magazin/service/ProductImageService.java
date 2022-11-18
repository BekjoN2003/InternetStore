package com.example.internet_magazin.service;

import com.example.internet_magazin.dto.image.ImageDto;
import com.example.internet_magazin.dto.productImage.ProductImageDto;
import com.example.internet_magazin.entity.ProductImage;
import com.example.internet_magazin.exception.BadRequest;
import com.example.internet_magazin.repository.ProductImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    private final ImageService imageService;

    public ProductImageService(ProductImageRepository productImageRepository, ImageService imageService) {
        this.productImageRepository = productImageRepository;
        this.imageService = imageService;
    }

    public ProductImage getEntity(Integer id) {
        Optional<ProductImage> optional = productImageRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BadRequest("Product Image not found");
        }
        return optional.get();
    }


    public ProductImageDto saveImage(MultipartFile file, Integer productId) {
        ProductImage productImage = getEntity(productId);
        ImageDto imageId = imageService.create(file);
        productImage.setProductId(productId);
        imageId.setId(productId);
        productImageRepository.save(productImage);
        return productImageRepository.findByProductIdAndImageId(productId, imageId);
    }

}
