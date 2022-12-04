package com.example.internet_magazin.config;

import com.example.internet_magazin.entity.Profile;
import com.example.internet_magazin.repository.ProfileRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    public CustomUserDetailsService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Keldi: loadUserByUsername");
        Optional<Profile> optional =this.profileRepository.findByEmailAndDeletedAtIsNull(username);
        optional.orElseThrow(() -> new UsernameNotFoundException("profile not found!"));
        Profile profile = optional.get();
        System.out.println(profile);
        return new CustomUserDetails(profile);
    }
}
