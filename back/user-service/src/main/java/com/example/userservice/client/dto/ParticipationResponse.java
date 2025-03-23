package com.example.userservice.client.dto;

import com.example.userservice.dto.GameResult;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParticipationResponse {
    Long matchId;
    Long participationId;
    Boolean attendance;
    GameResult gameResult;
    String status;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String category;
    String place;
}
