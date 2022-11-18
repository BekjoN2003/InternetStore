package com.example.internet_magazin.util;

import com.example.internet_magazin.config.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static Integer getProfileId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails profileDetails = (CustomUserDetails) authentication.getPrincipal();
        return profileDetails.getId();
    }
}
