package com.example.internet_magazin.repository;

import com.example.internet_magazin.dto.image.ImageDto;
import com.example.internet_magazin.dto.productImage.ProductImageDto;
import com.example.internet_magazin.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    ProductImageDto findByProductIdAndImageId(Integer productId, ImageDto imageId);
}
