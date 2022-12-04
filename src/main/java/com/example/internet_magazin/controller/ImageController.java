package com.example.internet_magazin.controller;


import com.example.internet_magazin.dto.image.ImageDto;
import com.example.internet_magazin.dto.productImage.ProductImageDto;
import com.example.internet_magazin.entity.Product;
import com.example.internet_magazin.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import lombok.AllArgsConstructor;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/image")
@AllArgsConstructor
public class ImageController {
    private ImageService imageService;


    @PostMapping("/create")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file){
        ImageDto result = imageService.saveToSystem(file);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/load/{filename:.+}")
    public @ResponseBody ResponseEntity<?> saveFile(@PathVariable String filename) throws IOException {
        Resource file = imageService.load(filename);
        return ResponseEntity.ok().header("Content-Disposition",
                "attachment; filename=" + "image.png").body(file);
    }

    @GetMapping(value = "/get/{link:.+}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable("link") String link) {
        if (link != null && link.length() > 0) {
            try {
                return imageService.getImg(link);
            } catch (Exception e) {
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return null;
    }

/*    @PostMapping("/{id}")
    public ResponseEntity<ProductImageDto> saveImage (MultipartFile multipartFile,
                                                      @PathVariable("id") Integer id,
                                                      Product productId){
        ProductImageDto result = imageService.saveImage(multipartFile, productId, id);
        return ResponseEntity.ok(result);
    }*/
}

