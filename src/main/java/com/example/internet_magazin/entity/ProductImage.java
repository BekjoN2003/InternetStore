package com.example.internet_magazin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "product_images")
@Table
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = ("product_id"), insertable = false, updatable = false)
    private Product product;
    @Column(name = ("product_id"))
    private Integer productId;
    @ManyToOne
    @JoinColumn(name = ("image_id"), insertable = false, updatable = false)
    private Image image;
    @Column(name = ("image_id"))
    private Integer imageId;
    @Column(name = ("create_at"))
    private LocalDateTime createdAt;
    @Column(name = ("update_at"))
    private LocalDateTime updatedAt;
    @Column(name = ("delete_at"))
    private LocalDateTime deletedAt;

}

