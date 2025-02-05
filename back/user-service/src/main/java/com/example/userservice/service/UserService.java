package com.example.userservice.service;

import com.example.userservice.auth.JwtIssuer;
import com.example.userservice.domain.User;
import com.example.userservice.dto.JwtDto;
import com.example.userservice.dto.SaveUserRequest;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtIssuer jwtIssuer;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email).get();
    }

    public User save(SaveUserRequest saveUserRequest) {
        return userRepository.save(saveUserRequest.toEntity());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public JwtDto socialSignIn(String email) {
        User user;

        user = findByEmail(email).get();
        return jwtIssuer.createToken(user.getEmail(), user.getRole().name());
    }



}
