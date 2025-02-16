package com.example.postservice.dto;

import com.example.postservice.client.dto.MatchResponse;
import com.example.postservice.domain.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class PostResponse {

    private String title;
    private String category;
    private Tag tag;
    private MatchResponse match;
    private String userName;
    private String userImage;

    private Timestamp createdDate;
    private Timestamp modifiedDate;
}
