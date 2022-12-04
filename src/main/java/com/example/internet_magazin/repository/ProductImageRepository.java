package com.example.internet_magazin.repository;

import com.example.internet_magazin.dto.image.ImageDto;
import com.example.internet_magazin.dto.productImage.ProductImageDto;
import com.example.internet_magazin.entity.Image;
import com.example.internet_magazin.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findAllByProductId(Integer productId);
}
