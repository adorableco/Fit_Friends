package com.example.fit_friends.dto;
import com.example.fit_friends.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddPostRequest {
    private String title;
    private String content;

    private Long userId;

    public Post toEntity() {
       return Post.builder()
               .title(title)
               .content(content)
               .build();
    }
}
