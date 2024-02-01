package com.example.fit_friends.controller;

import com.example.fit_friends.config.auth.JwtAuthProvider;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.service.AttendanceService;
import com.example.fit_friends.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AttendanceController {

    private final JwtAuthProvider jwtAuthProvider;
    private final UserService userService;
    private final AttendanceService attendanceService;

    @GetMapping("/api/qr/{matchId}")
    public ResponseEntity<byte[]> createQr(HttpServletRequest header, @PathVariable Long matchId) throws Exception {
        String token = header.getHeader("Authorization");
        User user = userService.findByEmail(jwtAuthProvider.getEmailbyToken(token)).get();
        return attendanceService.createQr(user, matchId);
    }

    @PostMapping("/api/attendance/{matchId}")
    public ResponseEntity<String> qrToAttendance(HttpServletRequest header, @PathVariable Long matchId) throws Exception {
        String token = header.getHeader("Authorization");
        String userEmail = jwtAuthProvider.getEmailbyToken(token);
        return attendanceService.qrToAttendance(matchId, userEmail);
    }
}
