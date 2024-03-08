package com.example.fit_friends.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "matches")
@JsonFilter("UserInfo")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Match_id",updatable = false)
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "User_id")
    @JsonIgnore
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

    @OneToOne
    @JoinColumn(name = "Tag_id")
    @ColumnDefault("null")
    private Tag tag;

    @Column(name = "start_time",nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date startTime;

    @Column(name = "end_time",nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date endTime;


    @Column(name = "Attendance_count",nullable = false)
    @ColumnDefault("0")
    private int attendanceCnt;


    @Builder

    public Match(Long matchId, User user, String category, int currentHeadCnt, int headCnt, String place, Tag tag, Date startTime, Date endTime, int attendanceCnt) {
        this.matchId = matchId;
        this.user = user;
        this.category = category;
        this.currentHeadCnt = currentHeadCnt;
        this.headCnt = headCnt;
        this.place = place;
        this.tag = tag;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendanceCnt = attendanceCnt;
    }
}
