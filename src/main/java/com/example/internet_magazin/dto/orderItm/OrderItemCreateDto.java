package com.example.internet_magazin.dto.orderItm;

import com.example.internet_magazin.dto.product.ProductDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemCreateDto {
    private ProductDto productDto;
    private Integer productId;
    private Integer orderId;
    private Integer amount;
    private Double price;
}
