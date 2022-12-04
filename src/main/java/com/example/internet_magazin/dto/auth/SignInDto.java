package com.example.internet_magazin.dto.auth;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Setter
public class SignInDto {
    @NotBlank(message = ("Name can not be null or empty"))
    @Length(message = ("Minimum size for name 2"), min = 2)
    private String name;
    @NotBlank(message = ("Surname can not be null or empty"))
    @Length(message = ("Minimum size for surname 3"), min = 3)
    private String surname;
    @NotBlank(message = "Email can't be empty or null")
    private String email;
    @NotBlank(message = ("Contact can't be empty or null"))
    private String contact;
    @NotBlank(message = ("Password can not be empty or null"))
    @Size(min = 8, message = ("Minimum Size for password 8"))
    private String password;

}
