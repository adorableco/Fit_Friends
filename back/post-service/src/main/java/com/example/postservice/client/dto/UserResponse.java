package com.example.postservice.client.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {
    private UUID userId;
    private String name;
    private String email;
    private String picture;
    private char gender;
    private String age;
    private String level;
    private float winningRate;
    private double attendanceRate;
    private Boolean isMyDetail;
}
