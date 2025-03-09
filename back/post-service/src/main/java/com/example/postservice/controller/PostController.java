package com.example.postservice.controller;

import com.example.postservice.common.dto.CustomResponseBody;
import com.example.postservice.common.resolver.userid.UserId;
import com.example.postservice.common.util.ResponseUtil;
import com.example.postservice.dto.*;
import com.example.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<CustomResponseBody<PostIdResponse>> savePost(@UserId UUID userId, @RequestBody AddPostRequest request) {
        PostIdResponse savedPost = postService.save(request, userId);
        return ResponseUtil.success(savedPost);
    }

    @GetMapping("")
    public ResponseEntity<CustomResponseBody<List<PostResponse>>> findPosts(@RequestParam("category") String category, @RequestParam String ageType, @RequestParam char genderType, @RequestParam String levelType) {
        List<PostResponse> posts = postService.findPosts(category, levelType, ageType, genderType);
        return ResponseUtil.success(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponseBody<PostResponse>> findPostById(@PathVariable Long id) {
        PostResponse response = postService.getPost(id);
        return ResponseUtil.success(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponseBody<Void>> deletePostById(@UserId UUID userId, @PathVariable Long id) {
        postService.deleteById(userId, id);
        return ResponseUtil.success(null);
    }

    @GetMapping("/matches/{matchId}")
    public ResponseEntity<CustomResponseBody<TagByPostResponse>> findTagByPost(@PathVariable Long matchId) {
        return ResponseUtil.success(postService.findTagByPost(matchId));

    }

    @PutMapping("/{postId}")
    public ResponseEntity<CustomResponseBody<PostIdResponse>> modifyPostById(@UserId UUID userId, @PathVariable Long postId, @RequestBody UpdatePostRequest dto) {
        return ResponseUtil.success(postService.updatePost(userId, postId, dto));
    }
}
