package com.example.match_service.client;

import com.example.match_service.client.dto.MatchResultRequest;
import com.example.match_service.client.dto.UserTagResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/users/{userId}")
    UserTagResponse getUserTagInfo(@PathVariable(name = "userId")UUID userId);

    @PostMapping("/users/game-results")
    MatchResultRequest postMatchResults();
}
