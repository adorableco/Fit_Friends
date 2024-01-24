package com.example.fit_friends.controller;

import com.example.fit_friends.dto.AddParticipationRequest;
import com.example.fit_friends.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ParticipationController {
    private final ParticipationService participationService;
    @PostMapping("/api/participation/{matchId}")
    ResponseEntity<String> applyMatch(@PathVariable Long matchId, @RequestBody AddParticipationRequest request){
        return ResponseEntity.ok()
                .body(participationService.save(matchId, request.getUserEmail()));
    }
}
