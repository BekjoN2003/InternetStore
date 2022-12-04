package com.example.internet_magazin.service;

import com.example.internet_magazin.dto.image.ImageDto;
import com.example.internet_magazin.entity.ProductImage;
import com.example.internet_magazin.repository.ProductImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ImageService imageService;

    public ProductImageService(ProductImageRepository productImageRepository, ImageService imageService) {
        this.productImageRepository = productImageRepository;
        this.imageService = imageService;
    }

    public List<ImageDto> getImage(Integer id) {
        List<ProductImage> list = productImageRepository.findAllByProductId(id);
        return list.stream().map(e -> imageService.getImageDto(e.getImageId())).collect(Collectors.toList());
    }

    public String create(Integer imageId, Integer productId) {
        ProductImage entity = new ProductImage();
        entity.setProductId(productId);
        entity.setImageId(imageId);
        productImageRepository.save(entity);
        return "Image saved to Product !";
    }

}
