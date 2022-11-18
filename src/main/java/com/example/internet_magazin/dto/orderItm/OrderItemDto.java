package com.example.internet_magazin.dto.orderItm;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter

public class OrderItemDto {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Integer amount;
    private Double price;




}
