package com.example.userservice.dto;

import com.example.userservice.domain.Role;
import com.example.userservice.domain.User;
import lombok.Getter;

@Getter
public class SaveUserRequest {

    private String name;
    private String email;
    private String picture;

    private char gender;

    private String level;
    private String age;
    private boolean genderVisible;
    private boolean ageVisible;



    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .level(level)
                .picture(picture)
                .gender(gender)
                .age(age)
                .genderVisible(genderVisible)
                .ageVisible(ageVisible)
                .build();
    }
}
