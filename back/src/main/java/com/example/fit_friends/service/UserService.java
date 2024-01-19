package com.example.fit_friends.service;

import com.example.fit_friends.config.auth.JwtIssuer;
import com.example.fit_friends.domain.Role;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.dto.JwtDto;
import com.example.fit_friends.dto.SaveUserRequest;
import com.example.fit_friends.dto.SignUpRequest;
import com.example.fit_friends.repository.UserRepository;
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

    public Optional<User> findById(Long id) { return userRepository.findById(id);}

    public JwtDto socialSignIn(String email) {
        User user;

        user = findByEmail(email).get();
        return jwtIssuer.createToken(user.getEmail(), user.getRole().name());

    }
}
