package com.example.fit_friends.controller;

import com.example.fit_friends.config.auth.JwtAuthProvider;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.service.ParticipationService;
import com.example.fit_friends.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ParticipationController {
    private final ParticipationService participationService;
    private final UserService userService;
    private final JwtAuthProvider jwtAuthProvider;
    @PostMapping("/api/participation/{matchId}")
    ResponseEntity<String> applyMatch(HttpServletRequest header, @PathVariable Long matchId){
        String token = header.getHeader("Authorization");
        User user = userService.findByEmail(jwtAuthProvider.getEmailbyToken(token)).get();
        return ResponseEntity.ok()
                .body(participationService.save(matchId, user));
    }
}
