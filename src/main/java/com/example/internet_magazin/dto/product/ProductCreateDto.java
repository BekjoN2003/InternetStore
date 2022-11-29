package com.example.internet_magazin.dto.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class ProductCreateDto {

    private Integer rate;

    @NotBlank(message = "Name can not be empty or null")
    private String name;

    @NotBlank(message = "Description can not be empty or null")
    private String description;

    @NotNull(message = "Please enter Price")
    private Double price;

    private Boolean visible;
}
