package com.example.fit_friends.dto;

import com.example.fit_friends.domain.Participation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private Boolean isMyDetail;

    private List<Participation> participationList;

    @Builder
    public LoadUserDetailResponse(String name, String email, String picture, char gender, String age, String level, float winningRate, float attendanceRate, List<Participation> participationList, Boolean isMyDetail) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.gender = gender;
        this.age = age;
        this.level = level;
        this.winningRate = winningRate;
        this.attendanceRate = attendanceRate;
        this.participationList = participationList;
        this.isMyDetail = isMyDetail;
    }
}