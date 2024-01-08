package com.example.fit_friends.dto;
import com.example.fit_friends.domain.Post;
import com.example.fit_friends.domain.Role;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddPostRequest {
    private String title;
    private String content;
    private User user;
    private String userEmail;
    private String category;



    public Post toEntity() {
       return Post.builder()
               .user(user)
               .category(category)
               .title(title)
               .content(content)
               .build();
    }
}
