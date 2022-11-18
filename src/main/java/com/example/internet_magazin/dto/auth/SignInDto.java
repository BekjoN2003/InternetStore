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
    @NotNull(message = ("Age can not be null"))
    @Min(value = 12, message = ("Minimum value for age 12"))
    @Max(value = 200, message = ("Maximum value for age 200"))
    private Integer age;
    @NotBlank(message = "Email can't be empty or null")
    private String email;
    @NotBlank(message = ("Password can not be empty or null"))
    @Size(min = 8, message = ("Minimum Size for password 8"))
    private String password;

}
