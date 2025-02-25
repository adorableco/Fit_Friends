package com.example.postservice.client.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MatchResponse {
    private Long matchId;
    private int currentHeadCnt;
    private int headCnt;
    private String place;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private UUID leaderId;
}
