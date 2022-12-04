package com.example.internet_magazin.dto.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDto {
    private String requirement;
    private String contact;
    private String address;
    private LocalDateTime deliveryDate;
    private LocalDateTime deliveryAt;


}
