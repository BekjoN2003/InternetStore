package com.example.internet_magazin.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

@Getter
@Setter
public class FilterDto {
    private Integer page;
    private Integer size;
    private String sortBy;
    private Sort.Direction direction;
    private LocalDateTime minCreatedDate;
    private LocalDateTime maxCreatedDate;
}
