package com.example.internet_magazin.dto.profile;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDto {
    private Integer id;
    private Integer imageId;
    private String name;
    private String surname;
    private String email;
    private String contact;
    private String password;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;



}
