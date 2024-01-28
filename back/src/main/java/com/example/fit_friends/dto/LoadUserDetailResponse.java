package com.example.fit_friends.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadUserDetailResponse {

    private String name;
    private String email;
    private String picture;
    private char gender;
    private String age;
    private String level;
    private float winningRate;
    private float attendanceRate;

    @Builder
    public LoadUserDetailResponse(String name, String email, String picture, char gender, String age, String level, float winningRate, float attendanceRate) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.gender = gender;
        this.age = age;
        this.level = level;
        this.winningRate = winningRate;
        this.attendanceRate = attendanceRate;
    }
}