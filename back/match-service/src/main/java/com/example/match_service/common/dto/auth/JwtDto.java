package com.example.match_service.common.dto.auth;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtDto {
    private String accessToken;
    private String refreshToken;

    private UUID userId;
    private String name;
    private String email;
    private String picture;
}
