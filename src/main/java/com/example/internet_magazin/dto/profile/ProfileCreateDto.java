package com.example.internet_magazin.dto.profile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProfileCreateDto {
    @NotBlank(message = "Name can not be empty or null")
    private String name;
    @NotBlank(message = "Surname can not be empty or null")
    private String surname;

    @NotBlank(message = "Email can not be empty or null")
    @Email
    private String email;
    @NotBlank(message = "Password can not be empty or null")
    private String password;

    @NotNull(message = "Please enter contact !")
    @NumberFormat
    private String contact;

}
