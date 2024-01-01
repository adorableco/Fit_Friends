package com.example.fit_friends.dto;

import com.example.fit_friends.domain.Match;
import com.example.fit_friends.domain.Post;
import com.example.fit_friends.domain.Tag;
import com.example.fit_friends.domain.User;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Getter
public class PostResponse {

    private final String category;
    private final String userName;
    private final Timestamp createdDate;
    private final Timestamp modifiedDate;
    private final String title;
    private final String content;
    private final int currentHeadCnt;
    private final int headCnt;
    private final String place;
    private final List<String> tag;

    public PostResponse(Post post, Tag tag, Match match, User user){
        this.category = match.getCategory();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.headCnt = match.getHeadCnt();
        this.currentHeadCnt = match.getCurrentHeadCnt();
        this.place = match.getPlace();
        this.tag = Arrays.asList(tag.getGenderType(), tag.getAgeType(), tag.getLevelType());
        this.title = post.getTitle();
        this.userName = user.getName();

    }
}
