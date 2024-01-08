package com.example.fit_friends.dto;
import com.example.fit_friends.domain.Match;
import com.example.fit_friends.domain.Post;
import com.example.fit_friends.domain.Role;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddPostRequest {
    private String title;
    private String content;
    private String category;
    private User user;
    private Match match;



    public Post toEntity() {
       return Post.builder()
               .category(category)
               .match(match)
               .user(user)
               .title(title)
               .content(content)
               .build();
    }
}
