package com.example.internet_magazin.entity;

import com.example.internet_magazin.type.ProfileStatus;
import com.example.internet_magazin.type.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity(name = "profiles")
@Table
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ("image_id"))
    private Image imageId;
    private String name;
    private String surname;
    @Email
    private String email;
    @UniqueElements( message = ("This contact already have"))
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
    @Column(name = ("created_at"))
    private LocalDateTime createdAt;
    @Column(name = ("email_verified_at"))
    private LocalDateTime emailVerifiedAt;
}
