package com.example.internet_magazin.entity;

import com.example.internet_magazin.type.ProfileStatus;
import com.example.internet_magazin.type.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "profiles")
@Table
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = ("image_id"))
    private Integer imageId;
    private String name;
    private String surname;
    private String email;
    private String contact;
    private String password;
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = ("deleted_at"))
    private LocalDateTime deletedAt;
    @Column(name = ("updated_at"))
    private LocalDateTime updatedAt;
    @Column(name = ("crated_at"))
    private LocalDateTime createdAt;



}
