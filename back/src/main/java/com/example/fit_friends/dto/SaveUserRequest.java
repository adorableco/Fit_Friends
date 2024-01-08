package com.example.fit_friends.dto;

import com.example.fit_friends.domain.Role;
import com.example.fit_friends.domain.User;
import lombok.Getter;

@Getter
public class SaveUserRequest {

    private String name;
    private String email;
    private String picture;

    private char gender;
    private String age;
    private boolean genderVisible;
    private boolean ageVisible;


    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .picture(picture)
                .gender(gender)
                .age(age)
                .genderVisible(genderVisible)
                .ageVisible(ageVisible)
                .build();
    }
}
