package com.example.userservice.controller;

import com.example.userservice.common.auth.SocialOAuth;
import com.example.userservice.domain.User;
import com.example.userservice.dto.JwtDto;
import com.example.userservice.dto.SaveUserRequest;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:19006", allowedHeaders = "*")
public class LoginController {

    @Autowired
    private final SocialOAuth socialOAuth;

    @Autowired
    private final UserService userService;



    @GetMapping("/api/login/{code}")
    public ResponseEntity<JwtDto> requestUserInfo(@PathVariable String code) throws Exception{

        String userInfo = socialOAuth.getUserInfo(code);
        JSONObject jsonObject = new JSONObject(userInfo);
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String picture = jsonObject.getString("picture");
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            JwtDto jwtDto = userService.socialSignIn(email);
            jwtDto.setUserId(user.get().getUserId());
            return ResponseEntity.ok()
                    .body(jwtDto);
        }else{
            return ResponseEntity.ok()
                    .body(JwtDto.builder()
                    .name(name)
                    .email(email)
                    .picture(picture)
                    .build());
        }

    }

    @PostMapping("/api/signup")
    public ResponseEntity<User> signUp(@RequestBody SaveUserRequest request) {
        User save = userService.save(request);
        try {
            return ResponseEntity.ok()
                    .body(save);
        }catch (Exception e){
            return ResponseEntity.internalServerError()
                    .body(save);
        }

    }


}