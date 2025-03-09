package com.example.match_service.service;

import com.example.match_service.client.PostServiceClient;
import com.example.match_service.client.UserServiceClient;
import com.example.match_service.client.dto.PostTagResponse;
import com.example.match_service.client.dto.UserTagResponse;
import com.example.match_service.common.exception.NotMatchLeaderException;
import com.example.match_service.common.exception.NotValidUserForMatchException;
import com.example.match_service.domain.Match;
import com.example.match_service.domain.Participation;
import com.example.match_service.dto.GameResultRequest;
import com.example.match_service.dto.ParticipationsResponse;
import com.example.match_service.dto.UserParticipationsResponse;
import com.example.match_service.repository.MatchRepository;
import com.example.match_service.repository.ParticipationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final MatchRepository matchRepository;
    private final ParticipationRepository participationRepository;
    private final PostServiceClient postServiceClient;
    private final UserServiceClient userServiceClient;


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

        UserTagResponse userTagResponse = userServiceClient.getUserTagInfo(userId);
        PostTagResponse postTagResponse = postServiceClient.getPostInfo(match.getId());

        if (!match.isAvailable()) {
            throw new IllegalArgumentException("정원이 가득찬 경기입니다.");
        }

        if (isUserValid(userTagResponse, postTagResponse)) {
            match.updateCurrentCnt();
            Participation participation = Participation.builder()
                    .matchId(matchId)
                    .userId(userId)
                    .build();
            participationRepository.save(participation);
        }
        else {
            throw new NotValidUserForMatchException();
        }
    }

    @Transactional
    public void updateMatchResult(UUID userId, Long matchId, List<GameResultRequest> results) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 매치 정보가 없습니다."));

        if (match.getLeaderId() != userId) {
            throw new NotMatchLeaderException();
        }

        for (GameResultRequest resultRequest : results) {
            Participation participation = participationRepository.findByMatchIdAndUserId(matchId,resultRequest.userId())
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 참여자 정보가 없습니다."));
            participation.updateMatchResult(resultRequest.result());
        }

        //TODO userService로 경기 결과 전송

    }

    @Transactional
    public List<ParticipationsResponse> getParticipations(UUID userId, Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 매치 정보가 없습니다."));
        if (match.getLeaderId() != userId) {
            throw new NotMatchLeaderException();
        }
        List<Participation> participations = participationRepository.findAllByMatchId(matchId);
        return participations.stream()
                .map(participation -> ParticipationsResponse.fromEntity(participation))
                .toList();
    }

    @Transactional
    public List<UserParticipationsResponse> getUserParticipations(UUID userId) {
        List<Participation> participations = participationRepository.findAllByUserId(userId);
        List<UserParticipationsResponse> responses = new ArrayList<>();
        for (Participation participation : participations) {
            Match match = matchRepository.findById(participation.getMatchId())
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 매치 정보가 없습니다."));
            responses.add(UserParticipationsResponse.fromEntity(participation,match));
        }

        return responses;
    }

    private boolean isUserValid (UserTagResponse userInfo, PostTagResponse postInfo) {
        return userInfo.age().equals(postInfo.ageType()) && userInfo.sex() == postInfo.genderType() && userInfo.level().equals(postInfo.levelType());
    }
}
