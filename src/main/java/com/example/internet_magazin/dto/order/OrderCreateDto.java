package com.example.internet_magazin.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderCreateDto {
    private String requirement;
    private String contact;
    private String address;

    private Integer profileId;

    private LocalDateTime deliveryDate;

    private LocalDateTime deliveryAt;
}
