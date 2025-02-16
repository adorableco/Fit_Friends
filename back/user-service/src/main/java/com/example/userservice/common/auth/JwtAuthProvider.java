package com.example.userservice.common.auth;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthProvider {
    private final JwtIssuer jwtIssuer;

    public UUID getIdByToken(String token) {
        Claims claims = jwtIssuer.getClaims(token);
        return UUID.fromString(jwtIssuer.getSubject(claims));
    }

}
