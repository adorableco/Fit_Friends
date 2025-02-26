package com.example.match_service.dto;

import com.example.match_service.domain.Match;
import java.time.LocalDateTime;
import java.util.UUID;

public record MatchResponse(Long matchId,
                            Integer currentHeadCnt,
                            Integer headCnt,
                            String place,
                            LocalDateTime startTime,
                            LocalDateTime endTime,
                            UUID leaderId) {

    public static MatchResponse fromEntity(Match match) {
        return new MatchResponse(match.getId(),
                match.getCurrentAttendanceCount(),
                match.getRequiredAttendanceCount(),
                match.getPlace(),
                match.getStartTime(),
                match.getEndTime(),
                match.getLeaderId());
    }
}
