package com.example.internet_magazin.controller;

import com.example.internet_magazin.dto.productImage.ProductImageDto;
import com.example.internet_magazin.service.ProductImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/product/image")
public class ProductImageController {
    private final ProductImageService productImageService;
    public ProductImageController(ProductImageService productImageService){
        this.productImageService = productImageService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProductImageDto> saveImage (MultipartFile multipartFile,@PathVariable("id") Integer productId){
        ProductImageDto result = productImageService.saveImage(multipartFile, productId);
        return ResponseEntity.ok(result);
    }






}

