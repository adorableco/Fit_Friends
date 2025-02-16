package com.example.match_service.service;

import com.example.match_service.domain.Match;
import com.example.match_service.repository.MatchRepository;
import com.example.match_service.repository.ParticipationRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final MatchRepository matchRepository;
    private final ParticipationRepository participationRepository;


    /**
     * 매치 참여 신청
     * @param matchId 참여하고자하는 경기
     * @param userId 참여하는 사용자 id
     * @return
     */
    @Transactional
    public void applyToMatch(Long matchId, UUID userId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 매치 정보가 없습니다."));

        //TODO user-service로부터 user 정보 조회
        //TODO post-service로부터 해당 경기의 tag 받아오기
        //TODO 신청하는 유저의 유효성 검증 필요
        //우선 보류 예정
    }
}
