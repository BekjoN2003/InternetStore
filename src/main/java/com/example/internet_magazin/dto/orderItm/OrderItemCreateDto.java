package com.example.internet_magazin.dto.orderItm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemCreateDto {

    private Integer orderId;
    private Integer amount;
    private Double price;
}
