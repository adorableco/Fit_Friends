package com.example.postservice.client.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class MatchResponse {
    private Long matchId;
    private int currentHeadCnt;
    private int headCnt;
    private String place;
    private Date startTime;
    private Date endTime;
    private UUID leaderId;
}
