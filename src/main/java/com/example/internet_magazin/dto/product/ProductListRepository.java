package com.example.internet_magazin.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductListRepository {
    private List<ProductDto> dtoList;
    private Long count;

}
