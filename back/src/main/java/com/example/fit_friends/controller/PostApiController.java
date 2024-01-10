package com.example.fit_friends.controller;
import com.example.fit_friends.domain.Post;
import com.example.fit_friends.dto.*;
import com.example.fit_friends.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/post")
    public ResponseEntity<Map<String,Long>> addPost(@RequestBody AddPostRequest request) {

        Long savedPost = postService.save(request);


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

    @DeleteMapping("/api/post/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id) {
        return ResponseEntity.ok().body(postService.deleteById(id));
    }

    @PutMapping("/api/post/{id}")
    public ResponseEntity<String> modifyPostById(@PathVariable Long id, @RequestBody AddPostRequest dto) {
        return ResponseEntity.ok().body(postService.updatePost(id,dto));
    }

}
