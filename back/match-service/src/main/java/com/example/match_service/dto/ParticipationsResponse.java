package com.example.match_service.dto;

import com.example.match_service.domain.Participation;
import java.util.UUID;

public record ParticipationsResponse(Long participationId, UUID userId, boolean hasParticipated) {
    public static ParticipationsResponse fromEntity(Participation participation) {
        return new ParticipationsResponse(
                participation.getId(),
                participation.getUserId(),
                participation.isAttendance());
    }
}
