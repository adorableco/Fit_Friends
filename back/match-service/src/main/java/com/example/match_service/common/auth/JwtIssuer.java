package com.example.match_service.common.auth;

import com.example.userservice.common.dto.auth.JwtDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtIssuer {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
    public static final long EXPIRE_TIME = 864000000;
    public static final long REFRESH_EXPIRE_TIME = 1000 * 60 * 15;
    public static final String KEY_ROLES = "roles";

    @PostConstruct
    void init(){
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public JwtDto createToken(UUID userId, String role) {

        Claims claims = Jwts.claims().setSubject(userId.toString());

        claims.put(KEY_ROLES, role);

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();


        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return JwtDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String getSubject(Claims claims) {
        return claims.getSubject();
    }

    public Claims getClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            log.info("claims = {}" , claims);
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
            log.info("claims = {}", claims);
        } catch (Exception e) {
            throw new BadCredentialsException("유효한 토큰이 아닙니다.");
        }
        return claims;
    }

}
