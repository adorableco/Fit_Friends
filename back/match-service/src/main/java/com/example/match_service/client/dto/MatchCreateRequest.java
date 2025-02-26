package com.example.match_service.client.dto;

import com.example.match_service.domain.Match;
import java.time.LocalDateTime;
import java.util.UUID;

public record MatchCreateRequest(UUID userId, String category, Integer headCnt,
                                 String place,
                                 LocalDateTime startTime,
                                 LocalDateTime endTime) {
    public Match toEntity() {
        return Match.builder()
                .leaderId(userId)
                .requiredAttendanceCount(headCnt)
                .startTime(startTime)
                .endTime(endTime)
                .place(place)
                .category(category)
                .build();
    }
}
