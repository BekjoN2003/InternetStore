package com.example.internet_magazin.entity;

import com.example.internet_magazin.type.ProductStatus;
import com.example.internet_magazin.type.ProductType;
import com.example.internet_magazin.type.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity(name = "products")
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer rate;
    private String name;
    private String description;
    private Double price;
    private Boolean visible;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    @Column(name = ("deleted_at"))
    private LocalDateTime deletedAt;
    @Column(name = ("updated_at"))
    private LocalDateTime updatedAt;
    @Column(name = ("created_at"))
    private LocalDateTime createdAt;


}
