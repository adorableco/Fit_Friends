package com.example.match_service.controller;

import com.example.match_service.common.dto.CustomResponseBody;
import com.example.match_service.common.dto.StatusCode;
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
    ResponseEntity<CustomResponseBody<Void>> applyMatch(@PathVariable Long matchId){
        //TODO 헤더로부터 userID 추출
        //우선은 임시값 설정
        UUID userId = UUID.fromString("4d68c186-5ae4-4cde-a9c8-549c71f46478");
        participationService.applyToMatch(matchId,userId);
        return ResponseEntity.ok(
                new CustomResponseBody<>(StatusCode.SUCCESS, "참가 신청되었습니다."));
    }
}
