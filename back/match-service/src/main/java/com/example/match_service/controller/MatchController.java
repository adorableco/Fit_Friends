package com.example.match_service.controller;

import com.example.match_service.common.dto.CustomResponseBody;
import com.example.match_service.common.dto.StatusCode;
import com.example.match_service.common.resolver.userid.UserId;
import com.example.match_service.service.ParticipationService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private ParticipationService participationService;

    @PostMapping("/participations/{matchId}")
    ResponseEntity<CustomResponseBody<Void>> applyMatch(@UserId UUID userId, @PathVariable Long matchId){
        //TODO 헤더로부터 userID 추출
        //우선은 임시값 설정
        participationService.applyToMatch(matchId,userId);
        return ResponseEntity.ok(
                new CustomResponseBody<>(StatusCode.SUCCESS, "참가 신청되었습니다."));
    }
}
