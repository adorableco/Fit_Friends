package com.example.postservice.client.dto;

import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
public class SaveMatchRequest {
    private UUID userId;
    private String category;
    private String place;
    private Date startTime;
    private Date endTime;
    private int headCnt;
}
