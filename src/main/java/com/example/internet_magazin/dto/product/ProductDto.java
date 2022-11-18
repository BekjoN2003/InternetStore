package com.example.internet_magazin.dto.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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


}
