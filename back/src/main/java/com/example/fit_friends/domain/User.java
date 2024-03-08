package com.example.fit_friends.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    private String email;

    @Column(nullable = false)
    private String picture;

    @Enumerated(EnumType.STRING)
    private Role role;

    private char gender;

    private String age;

    private String level;

    @Column
    private String accessToken;

    @ColumnDefault("1")
    private boolean genderVisible;

    @ColumnDefault("1")
    private boolean ageVisible;

    @ColumnDefault("0")
    private float winningRate;

    @ColumnDefault("0")
    private float attendanceRate;

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.getKey()));
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Builder

    public User(Long userId, String name, String email, String picture, Role role, char gender, String age, String level, String accessToken, boolean genderVisible, boolean ageVisible, float winningRate, float attendanceRate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.level = level;
        this.accessToken = accessToken;
        this.genderVisible = genderVisible;
        this.ageVisible = ageVisible;
        this.winningRate = winningRate;
        this.attendanceRate = attendanceRate;
    }
}
