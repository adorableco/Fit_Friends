package com.example.fit_friends.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;


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


    @ManyToOne
    @JoinColumn(name = "User_id")
    @CreatedBy
    private User user;

    @OneToOne
    @JoinColumn(name = "Tag_id")
    @ColumnDefault("null")
    private Tag tag;

    @OneToOne
    @JoinColumn(name = "Match_id")
    private Match match;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Content", nullable = false)
    private String content;


    @Column(nullable = false)
    private String category;
    @Builder

    public Post(Long postId, User user, Tag tag, Match match, String title, String content, String category) {
        this.postId = postId;
        this.user = user;
        this.tag = tag;
        this.match = match;
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
