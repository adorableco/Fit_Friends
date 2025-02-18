package com.example.match_service.controller;

import com.example.match_service.common.dto.CustomResponseBody;
import com.example.match_service.common.dto.StatusCode;
import com.example.match_service.common.resolver.userid.UserId;
import com.example.match_service.dto.GameResultRequest;
import com.example.match_service.dto.ParticipationsResponse;
import com.example.match_service.service.ParticipationService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipationController {

    private ParticipationService participationService;

    @PostMapping("/participations/{matchId}")
    public ResponseEntity<CustomResponseBody<Void>> applyMatch(@UserId UUID userId, @PathVariable Long matchId) {
        //TODO userservice로부터 userTagResponse 받기
        participationService.applyToMatch(matchId,userId);
        return ResponseEntity.ok(new CustomResponseBody<>(StatusCode.SUCCESS, "참가 신청되었습니다."));
    }

    //참여자 조회
    @GetMapping("/participations/{matchId}")
    public ResponseEntity<CustomResponseBody<List<ParticipationsResponse>>> getParticipations(
            @UserId UUID userId,
            @PathVariable Long matchId) {

        return ResponseEntity.ok(new CustomResponseBody<>(
                StatusCode.SUCCESS,
                "요청이 성공적으로 처리되었습니다.",
                participationService.getParticipations(userId, matchId)));
    }

    @PostMapping("/participations/{matchId}")
    public ResponseEntity<CustomResponseBody<Void>> updateGameResult(@UserId UUID userId,
                                                                     @PathVariable Long matchId,
                                                                     @RequestBody List<GameResultRequest> results) {
        participationService.updateMatchResult(userId, matchId, results);
        return ResponseEntity.ok(new CustomResponseBody<>(StatusCode.SUCCESS, "요청이 성공적으로 처리되었습니다."));
    }
}
