package com.example.fit_friends.controller;

import com.example.fit_friends.domain.Post;
import com.example.fit_friends.dto.AddPostRequest;
import com.example.fit_friends.dto.PostResponse;
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


    @PostMapping("/api/post")
    public ResponseEntity<Map<String,Long>> addPost(@RequestBody AddPostRequest addPostRequest) {

        Long savedPost = postService.save(addPostRequest.getUserEmail(), addPostRequest);

        Map<String,Long> response = new HashMap<>();
        response.put("postId",savedPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<PostResponse>> findAllPosts() {
        List<PostResponse> posts = postService.findAll()
                .stream()
                .map((Post post) -> new PostResponse(post,post.getTag(),post.getMatch(),post.getUser()))
                .toList();

        return ResponseEntity.ok()
                .body(posts);
    }

    @GetMapping("/api/post/{id}")
    public ResponseEntity<PostResponse> findPostById(@PathVariable Long id) {
        Optional<Post> byId = postService.findById(id);
        Post post = byId.orElse(null);

        return ResponseEntity.ok()
                .body(new PostResponse(post,post.getTag(),post.getMatch(),post.getUser()));
    }

}
