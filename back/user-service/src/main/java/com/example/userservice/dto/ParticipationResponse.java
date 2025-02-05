package com.example.userservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ParticipationResponse {
    Boolean attendance;
    Boolean isWin;
    String status;
    Date startTime;
    Date endTime;
    String category;
    String place;
}
