package com.example.userservice.controller;

import com.example.userservice.common.dto.CustomResponseBody;
import com.example.userservice.common.resolver.userid.UserId;
import com.example.userservice.common.util.ResponseUtil;
import com.example.userservice.domain.User;
import com.example.userservice.dto.*;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * User
 *
 * @author Seyeon Park
 * @date 2025. 03. 19
 * @description UserController 명세
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
public class UserController {
    private final UserService userService;

    /**
     * 사용자 회원가입 처리
     *
     * @param request 회원가입 요청 객체
     * @return 회원가입된 사용자 정보를 포함한 ResponseEntity
     */
    @PostMapping("/signup")
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

    /**
     * 특정 사용자의 상세 정보 조회
     *
     * @param userId 조회할 사용자 ID
     * @param me 현재 요청을 보낸 사용자 ID (인증된 사용자)
     * @return 사용자 상세 정보를 포함한 응답 객체
     */
    @GetMapping("/users/{userId}")
    ResponseEntity<CustomResponseBody<LoadUserDetailResponse>> loadUserDetail(@PathVariable UUID userId, @UserId UUID me) {
        LoadUserDetailResponse user = userService.findUser(userId, me);
        return ResponseUtil.success(user);
    }

    /**
     * 게임 결과를 적용하여 사용자 정보 업데이트
     *
     * @param request 게임 결과 정보가 포함된 요청 객체
     * @return 성공 응답 (결과 데이터 없음)
     */
    @PostMapping("/users/game-results")
    ResponseEntity<CustomResponseBody<String>> applyGameResult(@RequestBody ApplyGameResultRequest request) {
        userService.applyGameResult(request);
        return ResponseUtil.success(null);
    }

    /**
     * 사용자 상세 정보 수정
     *
     * @param memberId 수정할 사용자 ID (인증된 사용자)
     * @param request 수정할 사용자 정보가 포함된 요청 객체
     * @return 수정 결과 메시지
     */
    @PutMapping("/users")
    String modifyUserDetail(@UserId UUID memberId , @RequestBody ModifyUserDetailRequest request) {
        return userService.modifyUserDetail(request, memberId);
    }


}
