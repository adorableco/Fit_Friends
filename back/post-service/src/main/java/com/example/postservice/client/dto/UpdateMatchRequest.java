package com.example.postservice.client.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateMatchRequest {
    private Date startTime;
    private Date endTime;
    private int headCnt;
}
