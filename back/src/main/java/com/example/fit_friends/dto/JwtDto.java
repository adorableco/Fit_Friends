package com.example.fit_friends.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtDto {
    private String accessToken;
    private String refreshToken;
    private String name;
    private String email;
    private String picture;
}