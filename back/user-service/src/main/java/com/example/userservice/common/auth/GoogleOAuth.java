package com.example.userservice.common.auth;

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
    @Value("${google.userInfo_uri}")
    private String userInfo_uri;


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
