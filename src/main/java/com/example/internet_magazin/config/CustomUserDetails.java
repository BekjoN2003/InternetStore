package com.example.internet_magazin.config;

import com.example.internet_magazin.entity.Profile;
import com.example.internet_magazin.type.ProfileStatus;
import com.example.internet_magazin.type.Role;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    private Integer id;
    private String userName;
    private String password;
    private ProfileStatus status;
    private Role role;
    private List<GrantedAuthority> authorityList;

    public CustomUserDetails(Profile profile){
        this.id = profile.getId();
        this.userName = profile.getName();
        this.password = profile.getPassword();
        this.status = profile.getStatus();
        this.role = profile.getRole();

        this.authorityList = List.of(new SimpleGrantedAuthority(role.toString()));

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
