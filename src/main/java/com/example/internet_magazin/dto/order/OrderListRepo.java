package com.example.internet_magazin.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class OrderListRepo {
    private List<OrderDto> dtoList;

    private Long count;

}
