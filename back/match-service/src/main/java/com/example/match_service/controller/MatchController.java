package com.example.match_service.controller;

import com.example.match_service.common.dto.CustomResponseBody;
import com.example.match_service.client.dto.MatchCreateRequest;
import com.example.match_service.common.util.ResponseUtil;
import com.example.match_service.dto.MatchResponse;
import com.example.match_service.service.MatchService;
import com.example.match_service.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private ParticipationService participationService;
    private MatchService matchService;

    @PostMapping("/matches")
    public ResponseEntity<CustomResponseBody<Long>> postMatch(MatchCreateRequest request) {
        return ResponseUtil.success(matchService.createMatch(request));
    }

    @GetMapping("/matches/{matchId}")
    public ResponseEntity<CustomResponseBody<MatchResponse>> getMatch(@PathVariable Long matchId) {
        return ResponseUtil.success(matchService.getMatch(matchId));
    }
}
