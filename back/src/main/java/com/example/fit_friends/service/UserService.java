package com.example.fit_friends.service;

import com.example.fit_friends.domain.User;
import com.example.fit_friends.dto.SaveUserRequest;
import com.example.fit_friends.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User save(SaveUserRequest saveUserRequest) {
        return userRepository.save(saveUserRequest.toEntity());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) { return userRepository.findById(id);}
}
