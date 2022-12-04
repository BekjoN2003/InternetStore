package com.example.internet_magazin.service;

import com.example.internet_magazin.dto.profile.ProfileCreateDto;
import com.example.internet_magazin.dto.profile.ProfileDto;
import com.example.internet_magazin.dto.profile.ProfileFilterDto;
import com.example.internet_magazin.entity.Profile;
import com.example.internet_magazin.exception.BadRequest;
import com.example.internet_magazin.repository.ProfileRepository;
import com.example.internet_magazin.type.ProfileStatus;
import com.example.internet_magazin.type.Role;

import com.example.internet_magazin.util.JwtTokenUtil;
import com.example.internet_magazin.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final JwtTokenUtil jwtTokenUtil;
    private final ProfileRepository profileRepository;

    //_________________ ADMIN _________________\\

    public ProfileDto create(ProfileCreateDto dto) {
        Optional<Profile> optional = profileRepository.findByEmailAndDeletedAtIsNull(dto.getEmail());
        if (optional.isPresent()){
            throw new BadRequest("Profile already exist");
        }
        Profile profile = new Profile();
        profile.setSurname(dto.getSurname());
        profile.setStatus(ProfileStatus.INACTIVE);
        profile.setRole(Role.USER);
        profile.setName(dto.getName());
        profile.setEmail(dto.getEmail());
        profile.setCreatedAt(LocalDateTime.now());
        profile.setContact(dto.getContact());
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

    public ProfileDto convertToDto(Profile profile,
                                   ProfileDto dto){
        dto.setName(profile.getName());
        dto.setSurname(profile.getSurname());
        dto.setEmail(profile.getEmail());
        dto.setContact(profile.getContact());
        return dto;
    }

    public List<ProfileDto> filter(ProfileFilterDto dto) {
        String sortBy = ("createdAt");
        if (dto.getSortBy() != null){
            sortBy = dto.getSortBy();
        }
        PageRequest pageable = PageRequest.of(dto.getPage(), dto.getSize(), dto.getDirection(), sortBy);
        List<Predicate> predicateList = new ArrayList<>();
        Specification<Profile> specification =((root, query, criteriaBuilder) ->{
            if (dto.getName() != null) {
                predicateList.add(criteriaBuilder.like(root.get("name"), "%" +
                        dto.getName() + "%"));
            }
            if (dto.getSurname() != null){
                predicateList.add(criteriaBuilder.like(root.get("surname"),
                        "%" + dto.getSurname() + "%"));
            }
            if (dto.getMinCreatedDate() != null && dto.getMaxCreatedDate() != null) {
                predicateList.add(criteriaBuilder.between(root.get("createdAt"),
                        dto.getMinCreatedDate(), dto.getMaxCreatedDate()));
            }
            if (dto.getMinCreatedDate() == null && dto.getMaxCreatedDate() != null) {
                predicateList.add(criteriaBuilder.lessThan(root.get("createdAt"),
                        dto.getMinCreatedDate()));
            }
            if (dto.getMinCreatedDate() != null && dto.getMaxCreatedDate() == null) {
                predicateList.add(criteriaBuilder.greaterThan(root.get("createdAt"),
                        dto.getMaxCreatedDate()));
            }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        } );
        Page<Profile> page = profileRepository.findAll(specification, pageable);
        return page.stream().map(profile -> convertToDto(profile, new ProfileDto())).
                collect(Collectors.toList());
    }

    public Boolean isAdmin(){
        Profile profile = getEntity(SecurityUtil.getUserId());
        if (profile == null){
            throw new BadRequest("Profile not found");
        }
        else if (profile.getRole().equals(Role.ADMIN)) {
            return true;
        }
        return false;
    }


    public List<ProfileDto> getAll(Integer page,
                                   Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Profile> pages = profileRepository.findAll(pageable);
        return pages.stream().map(profile -> (convertToDto(profile, new ProfileDto()))).
                collect(Collectors.toList());
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
        return "Role updated !" + profile;
    }

    //================ USER ================//
    public String verification(String token) {
        String email = jwtTokenUtil.getUserName(token);
        if (!jwtTokenUtil.validate(token)) {
            throw new BadRequest("Token invalid--");
        }
       // Optional<Profile> optional = profileRepository.findById(Integer.valueOf(email));
        Optional<Profile> optional = profileRepository.findByEmailAndDeletedAtIsNull(email);
        if (optional.isEmpty()) {
            throw new BadRequest("Profile not found--");
        }
        Profile profile = optional.get();
        if (profile.getEmail() != null){
            throw new BadRequest("This email already verified");
        }
        if (profile.getEmailVerifiedAt() != null) {
            throw new BadRequest("User already verified");
        }
        profile.setEmail(email);
        profile.setEmailVerifiedAt(LocalDateTime.now());
        profileRepository.save(profile);
        return "Successful verified";
    }

    public ProfileDto update(ProfileDto dto, Integer id) {
        Profile profile = getEntity(id);
        if (dto.getEmail() != null){
            profile.setEmail(dto.getEmail());
        }

        if (dto.getName() != null){
            profile.setName(dto.getName());
        }
        if (dto.getContact() != null){
            profile.setContact(dto.getContact());
        }
        if (dto.getSurname() != null){
            profile.setSurname(dto.getSurname());
        }
        profile.setUpdatedAt(LocalDateTime.now());
        profile.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(profile);
        return convertToDto(profile, new ProfileDto());
    }
}
