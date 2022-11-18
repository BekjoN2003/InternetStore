package com.example.internet_magazin.dto.productImage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
public class ProductImageDto {
    private Integer id;
    private Integer productId;
    private Integer imageId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}

