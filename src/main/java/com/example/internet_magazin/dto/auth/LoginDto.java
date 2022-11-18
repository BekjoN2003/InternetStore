package com.example.internet_magazin.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginDto {
    @NotBlank(message = ("Email can't be null or empty"))
    private String email;
    @NotBlank(message = ("Password can't be null or empty"))
    private String password;
}
