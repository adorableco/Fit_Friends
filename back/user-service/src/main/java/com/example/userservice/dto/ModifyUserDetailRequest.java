package com.example.userservice.dto;

import lombok.Data;

@Data
public class ModifyUserDetailRequest {
    private String name;
    private boolean ageVisible;
    private boolean genderVisible;


}
