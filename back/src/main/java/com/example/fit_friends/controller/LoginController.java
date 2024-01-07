package com.example.fit_friends.controller;
import com.example.fit_friends.config.auth.GoogleOAuth;
import com.example.fit_friends.config.auth.SocialOAuth;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class LoginController{

    @Autowired
    private final SocialOAuth socialOAuth;
    private final HttpServletResponse response;

    @GetMapping("/api/login")
    public void loginGoogle() throws Exception{

        response.sendRedirect(socialOAuth.getOAuthRedirectUrl());
    }

    @GetMapping("/login/oauth2/code/google")
    public String requestToken(@RequestParam(name="code") String code) throws Exception{
        final String accessToken =  socialOAuth.requestAccessToken(code);

        return socialOAuth.getUserInfo(accessToken);
    }


}
