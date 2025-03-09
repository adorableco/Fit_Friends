package com.example.match_service.client;

import com.example.match_service.client.dto.PostTagResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "post-service")
public interface PostServiceClient {
    @GetMapping("/posts/matches/{id}")
    PostTagResponse getPostInfo(@PathVariable Long id);
}
