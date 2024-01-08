package com.example.fit_friends.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Match_id",updatable = false)
    private Long matchId;

    @OneToOne
    @JoinColumn(name = "Post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    @Column(name = "Category",nullable = false)
    private String category;

    @Column(name = "Current_head_cnt",nullable = false)
    @ColumnDefault("0")
    private int currentHeadCnt;

    @Column(name = "Head_cnt",nullable = false)
    private int headCnt;

    @Column(name = "Place", nullable = false)
    private String place;

    @Column(name = "Match_date",nullable = false)
    private Timestamp matchDate;

    @Column(name = "Attendance_count",nullable = false)
    @ColumnDefault("0")
    private int attendanceCnt;

    @Builder
    public Match(Long matchId, Post post, User user,
                 String category, int currentHeadCnt, int headCnt,
                 String place, Timestamp matchDate, int attendanceCnt) {
        this.matchId = matchId;
        this.post = post;
        this.user = user;
        this.category = category;
        this.currentHeadCnt = currentHeadCnt;
        this.headCnt = headCnt;
        this.place = place;
        this.matchDate = matchDate;
        this.attendanceCnt = attendanceCnt;
    }
}
