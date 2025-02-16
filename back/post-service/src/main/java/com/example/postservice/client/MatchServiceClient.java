package com.example.postservice.client;

import com.example.postservice.client.dto.MatchResponse;
import com.example.postservice.client.dto.SaveMatchRequest;
import com.example.postservice.client.dto.UpdateMatchRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "match-service")
public interface MatchServiceClient {

    @GetMapping("/{userId}/attendance-rate")
    double getAttendanceRate(@PathVariable(name = "userId") Long userId);

    @PostMapping("/matches")
    Long saveMatch(@RequestBody SaveMatchRequest matchRequest);

    @PatchMapping("/matches/{matchId}")
    void updateMatch(UpdateMatchRequest match, @PathVariable Long matchId);

    @GetMapping("/matches/{matchId}")
    MatchResponse getMatch(@PathVariable Long matchId);
}
