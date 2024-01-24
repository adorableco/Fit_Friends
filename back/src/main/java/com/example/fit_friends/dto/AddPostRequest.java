package com.example.fit_friends.dto;
import com.example.fit_friends.domain.*;
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
    private String category;

    private Match match;
    private Tag tag;

    public Post postToEntity(Tag tag, Match match, User user) {

       return Post.builder()
               .category(category)
               .tag(tag)
               .match(match)
               .user(user)
               .title(title)
               .content(content)
               .build();
    }

    public Match matchToEntity(Tag savedTag, User user) {
        return Match.builder()
                .user(user)
                .tag(savedTag)
                .category(category)
                .headCnt(match.getHeadCnt())
                .place(match.getPlace())
                .startTime(match.getStartTime())
                .endTime(match.getEndTime())
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
