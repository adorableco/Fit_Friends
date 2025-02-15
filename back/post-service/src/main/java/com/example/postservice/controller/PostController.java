package com.example.postservice.controller;

import com.example.postservice.common.dto.CustomResponseBody;
import com.example.postservice.common.resolver.userid.UserId;
import com.example.postservice.common.util.ResponseUtil;
import com.example.postservice.dto.AddPostRequest;
import com.example.postservice.dto.PostResponse;
import com.example.postservice.dto.PostIdResponse;
import com.example.postservice.dto.UpdatePostRequest;
import com.example.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins="http://localhost:19006", allowedHeaders = "*")
public class PostController {

    private final PostService postService;

    @PostMapping("/api/post")
    public ResponseEntity<CustomResponseBody<PostIdResponse>> savePost(@UserId UUID userId, @RequestBody AddPostRequest request) {
        PostIdResponse savedPost = postService.save(request, userId);
        return ResponseUtil.success(savedPost);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<CustomResponseBody<List<PostResponse>>> findPosts(@RequestParam("category") String category, @RequestParam String ageType, @RequestParam char genderType, @RequestParam String levelType) {
        List<PostResponse> posts = postService.findPosts(category, levelType, ageType, genderType);
        return ResponseUtil.success(posts);
    }

    @GetMapping("/api/post/{id}")
    public ResponseEntity<CustomResponseBody<PostResponse>> findPostById(@PathVariable Long id) {
        PostResponse response = postService.getPost(id);
        return ResponseUtil.success(response);
    }

    @DeleteMapping("/api/post/{id}")
    public ResponseEntity<CustomResponseBody<Void>> deletePostById(@UserId UUID userId, @PathVariable Long id) {
        postService.deleteById(userId, id);
        return ResponseUtil.success(null);
    }

    @PutMapping("/api/post/{postId}")
    public ResponseEntity<CustomResponseBody<PostIdResponse>> modifyPostById(@UserId UUID userId, @PathVariable Long postId, @RequestBody UpdatePostRequest dto) {
        return ResponseUtil.success(postService.updatePost(userId, postId, dto));
    }
}
