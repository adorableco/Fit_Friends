package com.example.match_service.domain;

import com.example.match_service.ENUM.Result;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 경기에 참여하는 사용자에 대한 entity
 */
@Getter
@Entity
@Table(name = "participations")
@NoArgsConstructor
public class Participation {
    @Id
    @Column(name = "participation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private UUID userId;

    @NotNull
    private Long matchId;

    @Enumerated(EnumType.STRING)
    private Result gameResult;

    private boolean isAttendance;

    private String status;

    @Builder
    public Participation(UUID userId, Long matchId) {
        this.userId = userId;
        this.matchId = matchId;
    }

    //TODO 경기정보 업데이트
    //TODO 승낙 현황 업데이트
    public void modifyParticipationStatus(Match match) {
        LocalDateTime now = LocalDateTime.now();

        //경기가 끝나면 end로 업데이트
        if (now.isAfter(match.getEndTime())) {
            updateStatus("end");
        }
        else if (!isAttendance) {
            updateStatus("accepted");
        }
    }

    public void updateAttendanceStatus(boolean isAttendance) {
        this.isAttendance = isAttendance;
    }

    private void updateStatus(String status) {
        this.status = status;
    }
}
