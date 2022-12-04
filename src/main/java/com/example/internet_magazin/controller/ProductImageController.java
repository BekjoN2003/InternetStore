package com.example.internet_magazin.controller;

import com.example.internet_magazin.service.ProductImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product/image")
public class ProductImageController {
    private final ProductImageService productImageService;
    public ProductImageController(ProductImageService productImageService){
        this.productImageService = productImageService;
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestParam ("image_id")Integer imageId,
                                 @RequestParam("product_id") Integer productId){
        String result = productImageService.create(imageId, productId);
        return ResponseEntity.ok(result);
    }

}

