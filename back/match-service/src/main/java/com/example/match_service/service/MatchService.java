package com.example.match_service.service;

import com.example.match_service.domain.Match;
import com.example.match_service.client.dto.MatchCreateRequest;
import com.example.match_service.dto.MatchResponse;
import com.example.match_service.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;

    @Transactional
    public Long createMatch(MatchCreateRequest request) {
        Match match = request.toEntity();
        return matchRepository.save(match).getId();
    }

    @Transactional
    public MatchResponse getMatch(Long matchId) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 매치 ID입니다."));
        return MatchResponse.fromEntity(match);
    }
}
