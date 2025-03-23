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

/**
 * Auth
 *
 * @author Seyeon Park
 * @date 2025. 03. 19
 * @description AuthController 명세
 * @history <pre>
 *  -----------------------------------------------------------------
 *      변경일          작성자                    변경내용
 *  --------------- ---------- --------------------------------------
 *   2025. 03. 19     Seyeon                  문서 첫 작성
 *
 *
 *  </pre>
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
    private final SocialOAuth socialOAuth;
    private final AuthService authService;

    /**
     * 소셜 로그인 후 사용자 정보를 요청하여 처리합니다.
     *
     * @param code 소셜 로그인 인증 코드
     * @return 사용자 인증 정보를 포함한 JWT 또는 기본 사용자 정보
     * @throws Exception 소셜 로그인 API 호출 중 오류가 발생할 경우 예외 발생
     */
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
