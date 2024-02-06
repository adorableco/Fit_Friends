package com.example.fit_friends.controller;

import com.example.fit_friends.dto.LoadUserDetailResponse;
import com.example.fit_friends.dto.ModifyUserDetailRequest;
import com.example.fit_friends.service.UserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:19006", allowedHeaders = "*")
public class UserDetailController {

    private final UserDetailService userDetailService;

    @Autowired
    public UserDetailController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping("/api/user/{userId}")
    ResponseEntity<LoadUserDetailResponse> loadUserDetail(HttpServletRequest header, @PathVariable Long userId) {
        String token = header.getHeader("Authorization");
        LoadUserDetailResponse user = userDetailService.findUser(token, userId);

        return ResponseEntity.ok()
                .body(user);
    }

    @PutMapping("/api/user")
    String modifyUserDetail(HttpServletRequest header, @RequestBody ModifyUserDetailRequest request) {
        String token = header.getHeader("Authorization");
        return userDetailService.modifyUserDetail(request, token);

    }
}
