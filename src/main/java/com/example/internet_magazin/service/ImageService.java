package com.example.internet_magazin.service;

import com.example.internet_magazin.dto.image.ImageDto;
import com.example.internet_magazin.dto.productImage.ProductImageDto;
import com.example.internet_magazin.entity.Image;
import com.example.internet_magazin.entity.Product;
import com.example.internet_magazin.entity.ProductImage;
import com.example.internet_magazin.exception.BadRequest;
import com.example.internet_magazin.repository.ImageRepository;
import com.example.internet_magazin.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;


import java.net.MalformedURLException;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Calendar;
import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {
    private String fileUrl = "uploads/images/";
    @Value("${serverAddress}")
    private String serverAddress;
    @Autowired
    private ImageRepository imageRepository;

    public ImageDto saveToSystem(MultipartFile file) {
        try {
            String YMD = getYMD();// year month day
            String type = file.getContentType().split("/")[1];
            String token = UUID.randomUUID().toString();

            String URL = YMD + "/" + token + "." + type;
            File folder = new File(fileUrl + YMD);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            Path path = Paths.get(fileUrl).resolve(URL);
            Files.copy(file.getInputStream(), path);

            return createImage(YMD, type, file.getSize(), token);
        } catch (IOException e) {
            throw new BadRequest("File not created");
        }
    }

    private ImageDto createImage(String ymd, String type, long size, String token) {
        Image image = new Image();
        image.setPath(ymd);
        image.setSize(size);
        image.setToken(token);
        image.setType(type);
        image.setUrl(serverAddress + token);
        image.setCreatedDate(LocalDateTime.now());
        imageRepository.save(image);
        ImageDto imageDto = new ImageDto();
        convertImageToDto(image, imageDto);
        return imageDto;
    }

    public ImageDto convertImageToDto(Image image, ImageDto imageDto) {
        imageDto.setSize(image.getSize());
        imageDto.setPath(image.getPath());
        imageDto.setUrl(image.getUrl());
        imageDto.setId(image.getId());
        imageDto.setToken(image.getToken());
        imageDto.setType(image.getType());
        return imageDto;
    }

    public String getYMD() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return String.format("%s/%s/%s", year, month + 1, day);
    }

    public Resource load(String token) {
        try {
            Image entity = getImage(token);
            Path file = Paths.get(fileUrl).resolve(entity.getPath() + "/" +
                    entity.getToken() + "." + entity.getType());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public Image getImage(String token) {
        return imageRepository.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Image not found"));
    }

    public Image getImage(Integer id) {
        return imageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Image not found"));
    }

    public byte[] getImg(String token) {
        try {
            Image entity = getImage(token);
            String path = fileUrl + "/" + entity.getPath() + "/" + entity.getToken() + "." + entity.getType();

            byte[] imageInByte;
            BufferedImage originalImage;
            try {
                originalImage = ImageIO.read(new File(path));
            } catch (Exception e) {
                return new byte[0];
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(originalImage, "png", baos);

            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public ImageDto create(MultipartFile file) {
        try {
            String YMD = getYMD();// year month day
            String type = file.getContentType().split("/")[1];
            String token = UUID.randomUUID().toString();

            String URL = YMD + "/" + token + "." + type;
            File folder = new File(fileUrl + YMD);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            Path path = Paths.get(fileUrl).resolve(URL);
            Files.copy(file.getInputStream(), path);

            return createImage(YMD, type, file.getSize(), token);
        } catch (IOException e) {
            throw new BadRequest("File not created");
        }
    }

    public ImageDto getImageDto(Integer id) {
        Image entity = getImage(id);
        ImageDto dto = new ImageDto();
        dto.setId(entity.getId());
        dto.setUrl(serverAddress + entity.getToken());
        return dto;
    }
}