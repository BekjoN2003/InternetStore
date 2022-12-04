package com.example.internet_magazin.controller;

import com.example.internet_magazin.dto.profile.ProfileCreateDto;
import com.example.internet_magazin.dto.profile.ProfileDto;
import com.example.internet_magazin.dto.profile.ProfileFilterDto;
import com.example.internet_magazin.service.ProfileService;
import com.example.internet_magazin.type.Role;
import com.example.internet_magazin.util.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController (ProfileService profileService){
        this.profileService = profileService;
    }

             //_________________ ADMIN _________________\\

    @PostMapping("/secured/create")
    public ResponseEntity<?> createProfile(@RequestBody @Valid ProfileCreateDto dto){
        ProfileDto result = profileService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable("id") Integer id){
        ProfileDto result = profileService.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/secured/filter")
    public ResponseEntity<?> getFilter(@RequestBody @Valid ProfileFilterDto dto){
        List<ProfileDto> result = profileService.filter(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/secured/getAll")
    public ResponseEntity<?> getAll(@RequestParam("page")Integer page,
                                    @RequestParam("size")Integer size){
        List<ProfileDto> result = profileService.getAll(page, size);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/secured/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable("id") Integer id){
        String result = profileService.delete(id);
        return ResponseEntity.ok(result);
    }
    @PatchMapping("/secured/setRole/{id}")
    public ResponseEntity<?> setRoleUser(@PathVariable("id") Integer id,
                                         @RequestParam("role") String role){
        String result = profileService.setRole(id, role);
        return ResponseEntity.ok(result);
    }



    //================ USER ================//
   @GetMapping("/verification/{token}")
    public ResponseEntity<?> verification(@PathVariable("token") String token){
        String result = profileService.verification(token);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid ProfileDto dto,
                                           @PathVariable("id") Integer id){
        ProfileDto result = profileService.update(dto , id);
        return ResponseEntity.ok(result);
    }


    /*@PutMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestParam("email") String email,
                                         HttpServletRequest request) {
        Integer userId = SecurityUtil.getProfileId();
        profileService.updateUserEmail(userId, email);
        return ResponseEntity.ok().build();
    }*/


}
