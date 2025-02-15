package com.example.postservice.client;

import com.example.postservice.client.dto.MatchResponse;
import com.example.postservice.client.dto.SaveMatchRequest;
import com.example.postservice.client.dto.UpdateMatchRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "match-service")
public interface MatchServiceClient {

    @GetMapping("/match-service/{userId}/attendance-rate")
    double getAttendanceRate(@PathVariable(name = "userId") Long userId);

    @PostMapping("/match-service/matches")
    Long saveMatch(@RequestBody SaveMatchRequest matchRequest);

    @PatchMapping("/match-service/matches/{matchId}")
    void updateMatch(UpdateMatchRequest match, @PathVariable Long matchId);

    @GetMapping("/match-service/matches/{matchId}")
    MatchResponse getMatch(@PathVariable Long matchId);
}
