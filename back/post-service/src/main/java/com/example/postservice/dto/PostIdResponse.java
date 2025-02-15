package com.example.postservice.dto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostIdResponse {
    @NonNull
    private Long postId;
}
