package com.example.internet_magazin.entity;

import com.example.internet_magazin.type.OrderStatus;
import com.example.internet_magazin.type.PaymentType;
import com.example.internet_magazin.type.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "orders")
@Table
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = ("profile_id"), insertable = false, updatable = false)
    private Profile profile;
    private String requirement;
    private String contact;
    private String address;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = ("delivery_date"), insertable = false, updatable = false)
    private LocalDateTime deliveryDate;
    @Column(name = ("delivery_at"), insertable = false, updatable = false)
    private LocalDateTime deliveryAt;
    @Column(name = ("deleted_at"))
    private LocalDateTime deletedAt;
    @Column(name = ("updated_at"))
    private LocalDateTime updatedAt;
    @Column(name = ("crated_at"))
    private LocalDateTime createdAt;


}
