package com.example.match_service.client.dto;

import java.util.UUID;

public record UserTagResponse(UUID userId, String level, String sex, String age) {
}
