package com.example.userservice.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
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

    @Value("${google.userInfo_uri}")
    private String userInfo_uri;


    @Override
    public String getOAuthRedirectUrl() {
        Map<String,Object> params = new HashMap<>();
        params.put("client_id",clientId);
        params.put("response_type","code");
        params.put("redirect_uri",redirectUri);
        params.put("scope","email profile");

        String paramsString = params.entrySet().stream()
                .map(x->x.getKey()+"="+x.getValue())
                .collect(Collectors.joining("&"));

        return googleBaseUri+"?"+paramsString;
    }

    @Override
    public String requestAccessToken(String code){

        RestTemplate restTemplate = new RestTemplate();

        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        params.put("client_id",clientId);
        params.put("grant_type","authorization_code");
        params.put("response_type","code");
        params.put("redirect_uri",redirectUri);
        params.put("scope","email profile");
        params.put("client_secret",clientSecret);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenUri, params, String.class);

        if(responseEntity.getStatusCode()== HttpStatus.OK){
            try{
                JSONObject jsonObject = new JSONObject(responseEntity.getBody());
                return jsonObject.getString("access_token");
            }catch (Exception e){
                return "구글 로그인 요청 실패";
            }


        }

        return "구글 로그인 요청 처리 실패";
    }

    @Override
    public String getUserInfo(String authorizationCode) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ authorizationCode);

        HttpEntity<String> request = new HttpEntity<>(headers);


        ResponseEntity<String> responseEntity = restTemplate.exchange(userInfo_uri, HttpMethod.GET,request,String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity.getBody();
        }

        return "구글 사용자 정보 로드 실패";

    }
}
