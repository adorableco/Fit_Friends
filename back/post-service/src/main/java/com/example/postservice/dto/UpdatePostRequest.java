package com.example.postservice.dto;

import com.example.postservice.client.dto.UpdateMatchRequest;
import com.example.postservice.domain.Tag;
import lombok.Data;

@Data
public class UpdatePostRequest {
    private String title;
    private String content;
    private String category;
    private UpdateMatchRequest match;
    private Tag tag;

}
