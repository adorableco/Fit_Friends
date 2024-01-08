package com.example.fit_friends.controller;

import com.example.fit_friends.domain.Match;
import com.example.fit_friends.domain.Post;
import com.example.fit_friends.dto.AddMatchRequest;
import com.example.fit_friends.dto.AddPostRequest;
import com.example.fit_friends.dto.AddPostandMatchRequest;
import com.example.fit_friends.dto.PostResponse;
import com.example.fit_friends.service.MatchService;
import com.example.fit_friends.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.LongFunction;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;
    private final MatchService matchService;

    @PostMapping("/api/post")
    public ResponseEntity<Map<String,Long>> addPost(@RequestBody AddPostandMatchRequest request) {
        Match savedMatch = matchService.save(request.getUserEmail(), request.getCategory(), request.getAddMatchRequest());
        Long savedPost = postService.save(savedMatch, request.getUserEmail(), request.getCategory(), request.getAddPostRequest());


        Map<String,Long> response = new HashMap<>();
        response.put("postId",savedPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<PostResponse>> findAllPosts() {
        List<PostResponse> posts = postService.findAll()
                .stream()
                .map((Post post) -> new PostResponse(post))
                .toList();

        return ResponseEntity.ok()
                .body(posts);
    }

    @GetMapping("/api/post/{id}")
    public ResponseEntity<PostResponse> findPostById(@PathVariable Long id) {
        Optional<Post> byId = postService.findById(id);
        Post post = byId.orElse(null);

        return ResponseEntity.ok()
                .body(new PostResponse(post));
    }

}
