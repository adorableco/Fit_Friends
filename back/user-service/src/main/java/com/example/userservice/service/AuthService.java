package com.example.userservice.service;

import com.example.userservice.common.auth.JwtIssuer;
import com.example.userservice.common.dto.auth.JwtDto;
import com.example.userservice.domain.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtIssuer jwtIssuer;


    public JwtDto socialSignIn(UUID userId) {
        User user = findByUserId(userId).get();
        return jwtIssuer.createToken(userId, user.getRole().name());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private Optional<User> findByUserId(UUID userId) {
        return userRepository.findByUserId(userId);
    }
}
