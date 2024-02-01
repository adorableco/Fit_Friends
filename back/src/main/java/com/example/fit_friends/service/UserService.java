package com.example.fit_friends.service;

import com.example.fit_friends.config.auth.JwtAuthProvider;
import com.example.fit_friends.config.auth.JwtIssuer;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.dto.JwtDto;
import com.example.fit_friends.dto.LoadUserDetailResponse;
import com.example.fit_friends.dto.SaveUserRequest;
import com.example.fit_friends.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
