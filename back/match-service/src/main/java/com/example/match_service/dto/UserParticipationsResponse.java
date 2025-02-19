package com.example.match_service.dto;

import com.example.match_service.ENUM.Result;
import com.example.match_service.domain.Match;
import com.example.match_service.domain.Participation;
import java.time.LocalDateTime;

public record UserParticipationsResponse(
        Long matchId,
        Long participationId,
        Boolean attendance,
        Result gameResult,
        String status,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String category,
        String place) {

    public static UserParticipationsResponse fromEntity(Participation participation, Match match) {
        return new UserParticipationsResponse(
                match.getId(),
                participation.getId(),
                participation.isAttendance(),
                participation.getGameResult(),
                participation.getStatus(),
                match.getStartTime(),
                match.getEndTime(),
                match.getCategory(),
                match.getPlace());
    }
}
