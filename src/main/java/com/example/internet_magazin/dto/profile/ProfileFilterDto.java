package com.example.internet_magazin.dto.profile;

import com.example.internet_magazin.dto.FilterDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileFilterDto extends FilterDto {
    private String name;
    private String surname;
    private String email;

}
