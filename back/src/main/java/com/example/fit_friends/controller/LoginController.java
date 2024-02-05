package com.example.fit_friends.controller;
import com.example.fit_friends.config.auth.GoogleOAuth;
import com.example.fit_friends.config.auth.SocialOAuth;
import com.example.fit_friends.domain.Role;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.dto.JwtDto;
import com.example.fit_friends.dto.MemberStatusResponse;
import com.example.fit_friends.dto.SaveUserRequest;
import com.example.fit_friends.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:19006", allowedHeaders = "*")
public class LoginController{

    @Autowired
    private final SocialOAuth socialOAuth;

    @Autowired
    private final UserService userService;

    private final HttpServletResponse response;

//    @GetMapping("/api/login")
//    public String loginGoogle() throws Exception{
//
//        return socialOAuth.getOAuthRedirectUrl();
//    }

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
    public ResponseEntity signUp(@RequestBody SaveUserRequest request) {
        User save = userService.save(request);
        try {
            return ResponseEntity.ok()
                    .header("Access-Control-Allow-Origin", "http://localhost:19006")
                    .body(save);
        }catch (Exception e){
            return ResponseEntity.internalServerError()
                    .header("Access-Control-Allow-Origin", "http://localhost:19006")
                    .body(save);
        }

    }


}