package com.example.fit_friends.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtDto {
    private String accessToken;
    private String refreshToken;

    private Long userId;
    private String name;
    private String email;
    private String picture;
}