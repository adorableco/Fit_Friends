package com.example.match_service.client.dto;

public record PostTagResponse(
        Long tagId,
        Long matchId,
        char genderType,
        String levelType,
        String ageType) {}

