package com.example.postservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Post_id", updatable = false)
    private Long postId;

    @JoinColumn(name = "User_id")
    @CreatedBy
    private UUID userId;

    @OneToOne
    @JoinColumn(name = "Tag_id")
    @ColumnDefault("null")
    private Tag tag;

    @JoinColumn(name = "Match_id")
    private Long matchId;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Content", nullable = false)
    private String content;


    @Column(nullable = false)
    private String category;
    @Builder

    public Post(Long postId, UUID userId, Tag tag, Long matchId, String title, String content, String category) {
        this.postId = postId;
        this.userId = userId;
        this.tag = tag;
        this.matchId = matchId;
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
