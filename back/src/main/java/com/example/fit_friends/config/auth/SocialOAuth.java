package com.example.fit_friends.config.auth;

public interface SocialOAuth {
    String getOAuthRedirectUrl();

    String requestAccessToken(String code);

    String getUserInfo(String code);
}
