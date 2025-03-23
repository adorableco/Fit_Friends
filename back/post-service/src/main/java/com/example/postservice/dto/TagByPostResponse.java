package com.example.postservice.dto;

public record TagByPostResponse(
        Long matchId,
        Long tagId,
        char genderType,
        String levelType,
        String ageType
) {}
