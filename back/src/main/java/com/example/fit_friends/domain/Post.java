package com.example.fit_friends.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;

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
    private Tag tag;

    @OneToOne
    @JoinColumn(name = "Match_id")
    private Match match;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Content", nullable = false)
    private String content;

    @Builder

    public Post(Long postId, User user, Tag tag, Match match, String title, String content) {
        this.postId = postId;
        this.user = user;
        this.tag = tag;
        this.match = match;
        this.title = title;
        this.content = content;
    }
}
