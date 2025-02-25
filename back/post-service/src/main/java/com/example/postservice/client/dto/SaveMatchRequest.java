package com.example.postservice.client.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SaveMatchRequest {
    private UUID userId;
    private String category;
    private String place;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int headCnt;
}
