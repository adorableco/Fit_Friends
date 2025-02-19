package com.example.userservice.controller;

import com.example.userservice.common.auth.SocialOAuth;
import com.example.userservice.common.dto.CustomResponseBody;
import com.example.userservice.common.util.ResponseUtil;
import com.example.userservice.domain.User;
import com.example.userservice.common.dto.auth.JwtDto;
import com.example.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:19006", allowedHeaders = "*")
public class AuthController {
    private final SocialOAuth socialOAuth;
    private final AuthService authService;

    @GetMapping("/login/{code}")
    public ResponseEntity<CustomResponseBody<JwtDto>> requestUserInfo(@PathVariable String code) throws Exception{

        String userInfo = socialOAuth.getUserInfo(code);
        JSONObject jsonObject = new JSONObject(userInfo);
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String picture = jsonObject.getString("picture");
        Optional<User> user = authService.findByEmail(email);

        if (user.isPresent()) {
            JwtDto jwtDto = authService.socialSignIn(user.get().getUserId());
            jwtDto.setUserId(user.get().getUserId());
            return ResponseUtil.success(jwtDto);
        }else{
            return ResponseUtil.success(
                    JwtDto.builder()
                    .name(name)
                    .email(email)
                    .picture(picture)
                    .build());
        }

    }

//    @GetMapping("/auth")
//    public ResponseEntity<CustomResponseBody<UserIdResponse>> getUserId(@RequestHeader(name = "Authorization") String jwtToken) {
//
//    }


}
