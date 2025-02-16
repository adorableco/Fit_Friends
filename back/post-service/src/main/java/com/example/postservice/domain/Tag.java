package com.example.postservice.domain;

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
    private char genderType;

    @Column(name = "Level")
    private String levelType;

    @Column(name = "Age")
    private String ageType;

    @Builder
    public Tag(char genderType, String levelType, String ageType) {
        this.genderType = genderType;
        this.levelType = levelType;
        this.ageType = ageType;
    }
}
