package com.example.match_service.controller;

import com.example.match_service.common.dto.CustomResponseBody;
import com.example.match_service.common.dto.StatusCode;
import com.example.match_service.common.resolver.userid.UserId;
import com.example.match_service.service.ParticipationService;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipationController {
    private ParticipationService participationService;

    @PostMapping("/participations/{matchId}")
    public ResponseEntity<CustomResponseBody<Void>> applyMatch(@UserId UUID userId, @PathVariable Long matchId){
        //TODO userservice로부터 userTagResponse 받기
        participationService.applyToMatch(matchId,userId);
        return ResponseEntity.ok(
                new CustomResponseBody<>(StatusCode.SUCCESS, "참가 신청되었습니다."));
    }
}
