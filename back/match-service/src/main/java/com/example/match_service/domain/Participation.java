package com.example.match_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "participations")
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long participationId;
    boolean isWin;
    private String status;  //TODO ENUM 논의
    private Long matchId;
}
