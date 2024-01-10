package com.example.fit_friends.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Tag_id",updatable = false)
    private Long tagId;


    @Column(name = "Sex")
    private String genderType;

    @Column(name = "Level")
    private String levelType;

    @Column(name = "Age")
    private String ageType;

    @Builder
    public Tag( String genderType, String levelType, String ageType) {
        this.genderType = genderType;
        this.levelType = levelType;
        this.ageType = ageType;
    }
}
