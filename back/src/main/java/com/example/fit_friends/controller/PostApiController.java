package com.example.fit_friends.controller;
import com.example.fit_friends.config.auth.JwtAuthProvider;
import com.example.fit_friends.config.auth.JwtIssuer;
import com.example.fit_friends.domain.Post;
import com.example.fit_friends.dto.*;
import com.example.fit_friends.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
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
@CrossOrigin(origins="http://localhost:19006", allowedHeaders = "*")
public class PostApiController {

    private final PostService postService;

    private final JwtAuthProvider jwtAuthProvider;


    @PostMapping("/api/post")
    public ResponseEntity<Map<String,Long>> addPost(HttpServletRequest header, @RequestBody AddPostRequest request) {
        String token = header.getHeader("Authorization");
        String email = jwtAuthProvider.getEmailbyToken(token);

        Long savedPost = postService.save(request, email);


        Map<String,Long> response = new HashMap<>();
        response.put("postId",savedPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<PostResponse>> findPosts(@RequestParam("category") String category, @RequestParam String ageType, @RequestParam char genderType, @RequestParam String levelType) {
        List<PostResponse> posts;

        posts = postService.findPosts(category, levelType, ageType, genderType)
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
    public ResponseEntity<String> deletePostById(HttpServletRequest header, @PathVariable Long id) {
        String token = header.getHeader("Authorization");
        String email = jwtAuthProvider.getEmailbyToken(token);
        return ResponseEntity.ok().body(postService.deleteById(email, id));
    }

    @PutMapping("/api/post/{id}")
    public ResponseEntity<String> modifyPostById(HttpServletRequest header, @PathVariable Long id, @RequestBody AddPostRequest dto) {
        String token = header.getHeader("Authorization");
        String email = jwtAuthProvider.getEmailbyToken(token);
        return ResponseEntity.ok().body(postService.updatePost(email,id,dto));
    }

}
