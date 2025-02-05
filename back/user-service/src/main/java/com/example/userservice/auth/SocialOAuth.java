package com.example.userservice.auth;

public interface SocialOAuth {
    String getOAuthRedirectUrl();

    String requestAccessToken(String code);

    String getUserInfo(String code);
}
