package com.example.match_service.common.auth;

import io.jsonwebtoken.Claims;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthProvider {
    private final JwtIssuer jwtIssuer;

    public UUID getIdByToken(String token) {
        Claims claims = jwtIssuer.getClaims(token);
        return UUID.fromString(jwtIssuer.getSubject(claims));
    }

}
