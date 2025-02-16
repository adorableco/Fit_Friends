package com.example.match_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "matches")
@NoArgsConstructor
public class Match extends BaseEntity {
    @Id
    @Column(name = "match_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull
//    private Integer attendanceCount;
    @NotNull
    private String category;

    //현재 참여한 이용자수 (participations 레코드 수 세는 방법 사용 X 예정)
    @NotNull
    @Column(name = "current_head_cnt")
    private Integer currentAttendanceCount;

    @NotNull
    @Column(name = "head_cnt")
    private Integer requiredAttendanceCount;

    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private UUID leaderId;

    @NotNull
    private String place;  //TODO 주소 타입 정형화

    @Builder
    public Match(String category, int currentAttendanceCount, int requiredAttendanceCount,
                 LocalDateTime startTime, LocalDateTime endTime,
                 UUID leaderId,
                 String place) {
        this.category = category;
        this.currentAttendanceCount = currentAttendanceCount;
        this.requiredAttendanceCount = requiredAttendanceCount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.leaderId = leaderId;
        this.place = place;
    }

    public void updateCurrentCnt() {
        this.currentAttendanceCount = this.currentAttendanceCount + 1;
    }
}
