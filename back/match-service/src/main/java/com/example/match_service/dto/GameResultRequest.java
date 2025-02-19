package com.example.match_service.dto;

import com.example.match_service.ENUM.Result;
import java.util.UUID;

public record GameResultRequest(UUID userId, Result result) {
}
