package com.example.internet_magazin.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResultDto {
    private String email;
    private String token;
}
