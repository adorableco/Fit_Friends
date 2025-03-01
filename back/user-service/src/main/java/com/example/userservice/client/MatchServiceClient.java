package com.example.userservice.client;

import com.example.userservice.client.dto.AttendanceRateResponse;
import com.example.userservice.client.dto.ParticipationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "match-service")
public interface MatchServiceClient {

    @GetMapping("/participations/users/{userId}")
    List<ParticipationResponse> getParticipationList(@PathVariable(name = "userId") UUID userId);

    @GetMapping("/matches/{userId}/attendance-rate")
    AttendanceRateResponse getAttendanceRate(@PathVariable(name = "userId") UUID userId);

}
