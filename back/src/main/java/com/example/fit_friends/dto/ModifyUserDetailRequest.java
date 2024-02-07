package com.example.fit_friends.dto;

import lombok.Data;

@Data
public class ModifyUserDetailRequest {
    private String name;
    private boolean ageVisible;
    private boolean genderVisible;


}
