package com.example.userservice.client;

import com.example.userservice.dto.ParticipationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "match-service")
public interface MatchServiceClient {

    @GetMapping("/match-service/{userId}")
    List<ParticipationResponse> getParticipationList(@PathVariable(name = "userId") Long userId);

    @GetMapping("/match-service/{userId}/attendance-rate")
    double getAttendanceRate(@PathVariable(name = "userId") Long userId);

}
