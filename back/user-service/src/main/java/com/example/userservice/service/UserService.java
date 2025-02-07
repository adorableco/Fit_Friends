package com.example.userservice.service;

import com.example.userservice.common.auth.JwtIssuer;
import com.example.userservice.domain.User;
import com.example.userservice.common.dto.auth.JwtDto;
import com.example.userservice.dto.SaveUserRequest;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtIssuer jwtIssuer;


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return findByUserId(UUID.fromString(id)).get();
    }

    public User save(SaveUserRequest saveUserRequest) {
        return userRepository.save(saveUserRequest.toEntity());
    }

    public Optional<User> findByUserId(UUID userId) {
        return userRepository.findByUserId(userId);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public JwtDto socialSignIn(UUID userId) {
        User user = findByUserId(userId).get();
        return jwtIssuer.createToken(userId, user.getRole().name());
    }



}
