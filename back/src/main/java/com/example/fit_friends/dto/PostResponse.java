package com.example.fit_friends.dto;

import com.example.fit_friends.domain.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class PostResponse {

    private String title;
    private String category;
    private Tag tag;
    private MatchResponse match;
    private String userName;
    private String userImage;

    private Timestamp createdDate;
    private Timestamp modifiedDate;


    @Builder
    public PostResponse(Post post) {
        this.title = post.getTitle();
        this.userName = post.getUser().getName();
        this.userImage = post.getUser().getPicture();
        this.category = post.getCategory();
        this.createdDate =post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.match = new MatchResponse(post.getMatch());
        this.tag = post.getTag();
    }
}
