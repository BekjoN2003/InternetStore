package com.example.internet_magazin.service;

import com.example.internet_magazin.dto.profile.ProfileDto;
import com.example.internet_magazin.dto.profile.ProfileFilterDto;
import com.example.internet_magazin.entity.Profile;
import com.example.internet_magazin.exception.BadRequest;
import com.example.internet_magazin.repository.ProfileRepository;
import com.example.internet_magazin.type.ProfileStatus;
import com.example.internet_magazin.type.Role;

import com.example.internet_magazin.util.JwtTokenUtil;
import com.example.internet_magazin.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;
    private final ProfileRepository profileRepository;

    public ProfileService(PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, ProfileRepository profileRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.profileRepository = profileRepository;
    }

    public ProfileDto create(ProfileDto dto) {
        Profile profile = new Profile();
        profile.setSurname(dto.getSurname());
        profile.setStatus(ProfileStatus.INACTIVE);
        profile.setRole(Role.USER);
        profile.setName(dto.getName());
        profile.setEmail(dto.getEmail());
        profile.setCreatedAt(LocalDateTime.now());
        profileRepository.save(profile);
        return convertToDto(profile, new ProfileDto());
    }


    public ProfileDto get(Integer id) {
        return convertToDto(getEntity(id), new ProfileDto());
    }

    public Profile getEntity(Integer id) {
        Optional<Profile> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BadRequest("Profile not found");
        }
        return optional.get();
    }

    public ProfileDto convertToDto(Profile profile, ProfileDto dto){
        dto.setName(profile.getName());
        dto.setSurname(profile.getSurname());
        dto.setEmail(profile.getEmail());
        dto.setContact(profile.getContact());
        return dto;
    }

    public String verification(String token) {
        Profile profile = profileRepository.getById(jwtTokenUtil.getUserID(token));
        if (profile == null){
            return "profile not found";
        }
        if (profile.getStatus().equals(ProfileStatus.BLOCKED)){
            return "Your profile is blocked";
        }
        if (jwtTokenUtil.getExpirationDate(token).after(new Date(System.currentTimeMillis()))){
            profile.setStatus(ProfileStatus.ACTIVE);
            profileRepository.save(profile);
            return "Successful verified";
        }return "please return verified";
    }

    public List<ProfileDto> filter(ProfileFilterDto dto) {
        String sortBy = ("createdAt");
        if (dto.getSortBy() != null){
            sortBy = dto.getSortBy();
        }
        PageRequest pageable = PageRequest.of(dto.getPage(), dto.getSize(), dto.getDirection(), sortBy);
        List<Predicate> predicateList = new ArrayList<>();
        Specification<Profile> specification =((root, query, criteriaBuilder) ->{
            if (dto.getName() != null){
                predicateList.add(criteriaBuilder.like(root.get("name"), "%" + dto.getName() + "%"));
            }
            if (dto.getSurname() != null){
                predicateList.add(criteriaBuilder.like(root.get("surname"), "%" + dto.getSurname() + "%"));
            }
            if (dto.getEmail() != null) {
                predicateList.add(criteriaBuilder.like(root.get("email"), "%" + dto.getEmail() + "%"));
            }
            if (dto.getDirection() != null){
                predicateList.add(criteriaBuilder.like(root.get("direction"), "%" + dto.getDirection() + "%"));
            }
            if (dto.getMinCreatedDate() != null && dto.getMaxCreatedDate() != null) {
                predicateList.add(criteriaBuilder.between(root.get("createdAt"),
                        dto.getMinCreatedDate(), dto.getMaxCreatedDate()));
            }
            if (dto.getMinCreatedDate() != null && dto.getMaxCreatedDate() == null) {
                predicateList.add(criteriaBuilder.greaterThan(root.get("createdAt"),
                        dto.getMinCreatedDate()));
            }
            if (dto.getMinCreatedDate() == null && dto.getMaxCreatedDate() != null) {
                predicateList.add(criteriaBuilder.lessThan(root.get("createdAt"),
                        dto.getMaxCreatedDate()));
            }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        } );
        Page<Profile> page = profileRepository.findAll(specification, pageable);
        return page.stream().map(profile -> convertToDto(profile, new ProfileDto())).collect(Collectors.toList());
    }

    public Boolean isAdmin(){
        Profile profile = getEntity(SecurityUtil.getProfileId());
        if (profile == null){
            throw new BadRequest("Profile not found");
        }
        else if (profile.getRole().equals(Role.ADMIN)) {
            return true;
        }
        return false;
    }


    public ProfileDto update(Integer profileId, ProfileDto dto) {
        Profile profile = getEntity(profileId);
        if (dto.getEmail() != null){
            profile.setEmail(dto.getEmail());
        }

        if (dto.getName() != null){
            profile.setName(dto.getName());
        }
        profile.setUpdatedAt(LocalDateTime.now());
        profile.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(profile);
        return convertToDto(profile, new ProfileDto());
    }

    public List<ProfileDto> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Profile> pages = profileRepository.findAll(pageRequest);
        return pages.stream().map(profile -> (convertToDto(profile, new ProfileDto()))).collect(Collectors.toList());
    }


    public String delete(Integer id) {
        Profile profile = getEntity(id);
        profile.setStatus(ProfileStatus.BLOCKED);
        profile.setDeletedAt(LocalDateTime.now());
        profileRepository.save(profile);
        return "Profile deleted !";
    }


    public String setRole(Integer id, String role) {
        Profile profile = getEntity(id);
        profile.setRole(Role.valueOf(role));
        profileRepository.save(profile);
        return "Role updated !";
    }
}
