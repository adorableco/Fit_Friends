package com.example.postservice.dto;
import com.example.postservice.client.dto.SaveMatchRequest;
import com.example.postservice.domain.Post;
import com.example.postservice.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddPostRequest {
    private String title;
    private String content;
    private String category;

    private SaveMatchRequest match;
    private Tag tag;

    public Post postToEntity(Tag tag, Long matchId, UUID userId) {

       return Post.builder()
               .category(category)
               .tag(tag)
               .matchId(matchId)
               .userId(userId)
               .title(title)
               .content(content)
               .build();
    }

    public Tag tagToEntity() {
        return Tag.builder()
                .ageType(tag.getAgeType())
                .genderType(tag.getGenderType())
                .levelType(tag.getLevelType())
                .build();
    }
}
