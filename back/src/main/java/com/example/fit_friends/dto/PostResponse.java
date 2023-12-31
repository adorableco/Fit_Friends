package com.example.fit_friends.dto;

import com.example.fit_friends.domain.*;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Getter
public class PostResponse extends BaseEntity {

    private final Long postId;

    private final String category;
    private final String userName;
    private final String title;
    private final String content;
    private final int currentHeadCnt;
    private final int headCnt;
    private final String place;
    private final List<String> tag;

    public PostResponse(Post post, Tag tag, Match match, User user){
        this.postId = post.getPostId();
        this.category = match.getCategory();
        this.content = post.getContent();
        this.headCnt = match.getHeadCnt();
        this.currentHeadCnt = match.getCurrentHeadCnt();
        this.place = match.getPlace();
        this.tag = Arrays.asList(tag.getGenderType(), tag.getAgeType(), tag.getLevelType());
        this.title = post.getTitle();
        this.userName = user.getName();

    }
}
