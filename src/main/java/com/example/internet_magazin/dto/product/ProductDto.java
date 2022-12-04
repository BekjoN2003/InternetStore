package com.example.internet_magazin.dto.product;

import com.example.internet_magazin.dto.image.ImageDto;
import com.example.internet_magazin.entity.Image;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProductDto {
    private Integer id;
    private Integer rate;
    private String name;
    private String description;
    private Double price;
    private Boolean visible;
    private LocalDateTime createdAt;
    private List<ImageDto> imageList;


}
