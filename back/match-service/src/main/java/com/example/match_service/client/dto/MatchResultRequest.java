package com.example.match_service.client.dto;

import com.example.match_service.ENUM.Result;
import java.util.UUID;

public record MatchResultRequest(UUID userId, Result result, boolean hasParticipated) {
}
