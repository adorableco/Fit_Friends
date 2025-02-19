package com.example.userservice.dto;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class ApplyGameResultRequest {
    private List<GameResultDto> gameresults;

    @Getter
    public static class GameResultDto {
        private UUID userId;
        private GameResult result;
    }
}
