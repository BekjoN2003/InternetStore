package com.example.internet_magazin.service;

import com.example.internet_magazin.dto.auth.LoginDto;
import com.example.internet_magazin.dto.auth.LoginResultDto;
import com.example.internet_magazin.dto.auth.SignInDto;
import com.example.internet_magazin.entity.Profile;
import com.example.internet_magazin.exception.BadRequest;
import com.example.internet_magazin.repository.ProfileRepository;
import com.example.internet_magazin.type.ProfileStatus;
import com.example.internet_magazin.type.Role;
import com.example.internet_magazin.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final MailSenderService mailSenderService;
    @Value(" ${auth.verification.api}")
    private String verificationLinc;


    public AuthService(ProfileRepository profileRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, MailSenderService mailSenderService) {
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.mailSenderService = mailSenderService;
    }

    public boolean sendMessageToEmail(Profile profile) {
        String token = jwtTokenUtil.generateAccessToken(profile.getId(), profile.getEmail());
        String link = verificationLinc + token;
        String subject = "Website Verification";
        String content = "Click for verification this link: " + link + LocalDateTime.now();
        try {
            mailSenderService.send(profile.getEmail(), subject, content);
        } catch (Exception e) {
            profileRepository.delete(profile);
            return false;
        }

        return true;
    }


    public String singIn(SignInDto dto) {
        Optional<Profile> optional = profileRepository.findByEmailAndDeletedAtIsNull(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequest("Profile whit this email already exist");
        }

        Profile profile = new Profile();
        profile.setName(dto.getName());
        profile.setEmail(dto.getEmail());
        profile.setPassword(passwordEncoder.encode(dto.getPassword()));
        profile.setCreatedAt(LocalDateTime.now());
        profile.setRole(Role.USER);
        profileRepository.save(profile);
        if (sendMessageToEmail(profile)) {
            return "Please confirm your email";
        }
        return "Failed to send message";
    }

    public LoginResultDto login(LoginDto dto) {
        Optional<Profile> optional = profileRepository.findByEmailAndDeletedAtIsNull(dto.getEmail());
        if (optional.isEmpty()) {
            throw new BadRequest("Profile not found");
        }

        Profile profile = optional.get();
        if (!passwordEncoder.matches(dto.getPassword(), profile.getPassword())) {
            throw new BadRequest("User's password not found");
        }
        String token = jwtTokenUtil.generateAccessToken(profile.getId(), profile.getEmail());
        return new LoginResultDto(profile.getEmail(), token);
    }

    public String verification(String token) {
        Profile profile = profileRepository.getById(jwtTokenUtil.getUserID(token));
        if (profile == null) {
            return "Profile not found"; 
        }
        if (profile.getStatus().equals(ProfileStatus.BLOCKED)) {
            return "Your profile is blocked";
        }
        if (jwtTokenUtil.getExpirationDate(token).after(new Date(System.currentTimeMillis()))) {
            profile.setStatus(ProfileStatus.ACTIVE);
            profileRepository.save(profile);
            return "Successful verified";
        }
        return "please return verified";
    }
}
