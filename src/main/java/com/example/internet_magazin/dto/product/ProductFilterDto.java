package com.example.internet_magazin.dto.product;

import com.example.internet_magazin.dto.FilterDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductFilterDto extends FilterDto {
    private Integer rate;
    private String name;
    private String description;
    private Double price;
}
