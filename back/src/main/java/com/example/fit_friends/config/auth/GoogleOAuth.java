package com.example.fit_friends.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleOAuth implements SocialOAuth{

    @Value("${google.client.id}")
    private String clientId;
    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Value("${google.base.uri}")
    private String googleBaseUri;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.token_uri}")
    private String tokenUri;
    @Override
    public String getOAuthRedirectUrl() {
        Map<String,Object> params = new HashMap<>();
        params.put("client_id",clientId);
        params.put("response_type","code");
        params.put("redirect_uri",redirectUri);
        params.put("scope","profile");

        String paramsString = params.entrySet().stream()
                .map(x->x.getKey()+"="+x.getValue())
                .collect(Collectors.joining("&"));

        return googleBaseUri+"?"+paramsString;
    }

    @Override
    public String requestAccessToken(String code) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        params.put("client_id",clientId);
        params.put("grant_type","authorization_code");
        params.put("response_type","code");
        params.put("redirect_uri",redirectUri);
        params.put("scope","profile");
        params.put("client_secret",clientSecret);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenUri, params, String.class);

        if(responseEntity.getStatusCode()== HttpStatus.OK){
            return responseEntity.getBody();
        }

        return "구글 로그인 요청 처리 실패";
    }
}
