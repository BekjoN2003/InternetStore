package com.example.internet_magazin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = ("order_items"))
@Table
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = ("order_id"), insertable = false, updatable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name = ("product_id"), insertable = false, updatable = false)
    private Product productId;
    @Column(name = ("order_id"))
    private Integer orderId;
    private Integer product;
    private Integer amount;
    private Double price;
    @Column(name = ("deleted_at"))
    private LocalDateTime deletedAt;
    @Column(name = ("updated_at"))
    private LocalDateTime updatedAt;
    @Column(name = ("crated_at"))
    private LocalDateTime createdAt;

}
