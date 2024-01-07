package com.example.fit_friends.controller;
import com.example.fit_friends.config.auth.GoogleOAuth;
import com.example.fit_friends.config.auth.SocialOAuth;
import com.example.fit_friends.domain.Role;
import com.example.fit_friends.domain.User;
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
public class LoginController{

    @Autowired
    private final SocialOAuth socialOAuth;

    @Autowired
    private final UserService userService;

    private final HttpServletResponse response;

    @GetMapping("/api/login")
    public void loginGoogle() throws Exception{

        response.sendRedirect(socialOAuth.getOAuthRedirectUrl());
    }

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<MemberStatusResponse> requestUserInfo(@RequestParam(name="code") String code) throws Exception{
        final String accessToken =  socialOAuth.requestAccessToken(code);
        String userInfo = socialOAuth.getUserInfo(accessToken);
        try{
            JSONObject jsonObject = new JSONObject(userInfo);
            Optional<User> user = userService.findByEmail(jsonObject.getString("email"));

            if (!user.isPresent()) {
                String name = jsonObject.getString("name");
                String email = jsonObject.getString("email");
                String picture = jsonObject.getString("picture");

                return ResponseEntity.ok()
                        .body(new MemberStatusResponse(Role.GUEST.name(), name, email,picture,accessToken));
            }else{
                return ResponseEntity.internalServerError()
                        .body(new MemberStatusResponse(Role.USER.name(),accessToken));
            }

        }catch (Exception e){
            return ResponseEntity.internalServerError()
                    .body(new MemberStatusResponse(Role.GUEST.name(),"false","false","false","false"));
        }
    }

    @PostMapping("/api/signup")
    public ResponseEntity signUp(@RequestBody SaveUserRequest request) {
        User save = userService.save(request);
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(save);
        }catch (Exception e){
            return ResponseEntity.internalServerError()
                    .body(save);
        }

    }


}
