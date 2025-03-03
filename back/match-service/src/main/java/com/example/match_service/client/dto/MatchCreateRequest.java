package com.example.match_service.client.dto;

import com.example.match_service.domain.Match;
import java.time.LocalDateTime;
import java.util.UUID;

public record MatchCreateRequest(UUID userId, String category, Integer headCnt,
                                 String place,
                                 LocalDateTime startTime,
                                 LocalDateTime endTime) {
    public static Match toEntity(MatchCreateRequest request) {
        return Match.builder()
                .leaderId(request.userId)
                .requiredAttendanceCount(request.headCnt)
                .startTime(request.startTime)
                .endTime(request.endTime)
                .place(request.place)
                .category(request.category)
                .build();
    }
}
