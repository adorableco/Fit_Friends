package com.example.fit_friends.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TagFilteringRequest {
    char genderType;
    String levelType;
    String ageType;

    public TagFilteringRequest(char genderType, String levelType, String ageType) {
        this.genderType = genderType;
        this.levelType = levelType;
        this.ageType = ageType;
    }
}
