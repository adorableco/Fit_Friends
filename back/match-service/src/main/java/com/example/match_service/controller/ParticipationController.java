package com.example.match_service.controller;

import com.example.match_service.common.dto.CustomResponseBody;
import com.example.match_service.common.resolver.userid.UserId;
import com.example.match_service.common.util.ResponseUtil;
import com.example.match_service.dto.GameResultRequest;
import com.example.match_service.dto.ParticipationsResponse;
import com.example.match_service.dto.UserParticipationsResponse;
import com.example.match_service.service.ParticipationService;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParticipationController {
    private final ParticipationService participationService;

    @PostMapping("/participations/{matchId}")
    public ResponseEntity<CustomResponseBody<Void>> applyMatch(@UserId UUID userId, @PathVariable Long matchId) {
        participationService.applyToMatch(matchId,userId);
        return ResponseUtil.success();
    }

    //참여자 조회
    @GetMapping("/participations/matches/{matchId}")
    public ResponseEntity<CustomResponseBody<List<ParticipationsResponse>>> getParticipations(
            @UserId UUID userId,
            @PathVariable Long matchId) {

        return ResponseUtil.success(participationService.getParticipations(userId, matchId));
    }

    @PostMapping("/participations/{matchId}/match-results")
    public ResponseEntity<CustomResponseBody<Void>> updateGameResult(@UserId UUID userId,
                                                                     @PathVariable Long matchId,
                                                                     @RequestBody List<GameResultRequest> results) {
        participationService.updateMatchResult(userId, matchId, results);
        return ResponseUtil.success();
    }

    @GetMapping("/participations/users/{userId}")
    public ResponseEntity<CustomResponseBody<List<UserParticipationsResponse>>> getUserParticipations(@PathVariable UUID userId) {
        return ResponseUtil.success(participationService.getUserParticipations(userId));
    }
}
