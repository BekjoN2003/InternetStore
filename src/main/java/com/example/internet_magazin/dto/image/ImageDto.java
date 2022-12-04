package com.example.internet_magazin.dto.image;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageDto {
    private Integer id;
    private String path;
    private String type;
    private Long size;
    private String url;
    private String token;


}
