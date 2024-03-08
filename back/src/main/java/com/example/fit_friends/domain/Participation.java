package com.example.fit_friends.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "participations")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Participation_id",updatable = false)
    private Long participationId;

    @ManyToOne
    @JoinColumn(name = "User_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "Match_id")
    private Match match;

    @Column(name = "Status")
    private String status;

    @Column(name = "Attendance", nullable = false)
    @ColumnDefault("0")
    private boolean attendance;

    @Column(name = "Iswin", nullable = false)
    @ColumnDefault("0")
    private boolean isWin;

    public void modifyParticipationStatus() {
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(new java.sql.Timestamp(this.match.getEndTime().getTime()).toLocalDateTime())) {
            setStatus("end");
        } else if (!attendance) {
            setStatus("accepted");
        }
    }

    @Builder
    public Participation(Long participationId, User user, Match match,
                         String status, boolean attendance, boolean isWin) {
        this.participationId = participationId;
        this.user = user;
        this.match = match;
        this.status = status;
        this.attendance = attendance;
        this.isWin = isWin;
    }
}
