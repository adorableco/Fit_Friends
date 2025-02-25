package com.example.postservice.client.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateMatchRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int headCnt;
}
