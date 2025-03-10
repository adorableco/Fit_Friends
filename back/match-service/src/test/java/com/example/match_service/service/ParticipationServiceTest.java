package com.example.match_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.match_service.client.PostServiceClient;
import com.example.match_service.client.UserServiceClient;
import com.example.match_service.client.dto.PostTagResponse;
import com.example.match_service.client.dto.UserTagResponse;
import com.example.match_service.common.exception.NotValidUserForMatchException;
import com.example.match_service.domain.Match;
import com.example.match_service.repository.MatchRepository;
import com.example.match_service.repository.ParticipationRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParticipationServiceTest {

    @Mock
    private ParticipationRepository participationRepository;
    @Mock
    private MatchRepository matchRepository;
    @Mock
    private PostServiceClient postServiceClient;

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private ParticipationService participationService;

    @Test
    @DisplayName("매치 참여 성공 테스트")
    void applyToMatch() {
        //Given
        Match match = Match.builder()
                .category("soccer")
                .currentAttendanceCount(5)
                .requiredAttendanceCount(6)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusDays(1))
                .leaderId(UUID.randomUUID())
                .place("잠실 축구장")
                .build();

        match.setId(1L);

        PostTagResponse postTag = new PostTagResponse(1L,match.getId(),'F',"beginner","adult");
        UserTagResponse userTag = new UserTagResponse(UUID.randomUUID(), "beginner", 'F', "adult");

        //when
        when(matchRepository.findById(any())).thenReturn(Optional.of(match));
        when(postServiceClient.getPostInfo(match.getId())).thenReturn(postTag);
        when(userServiceClient.getUserTagInfo(any())).thenReturn(userTag);

        participationService.applyToMatch(match.getId(),userTag.userId());

        //Then
        assertEquals(6, match.getCurrentAttendanceCount());

        assertNotNull(participationRepository.findByMatchIdAndUserId(match.getId(), userTag.userId()));
    }

    @Test
    @DisplayName("매치 참여 실패 테스트 : 정원 초과")
    void applyToMatchFail() {
        //Given
        Match match = Match.builder()
                .category("soccer")
                .currentAttendanceCount(6)
                .requiredAttendanceCount(6)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusDays(1))
                .leaderId(UUID.randomUUID())
                .place("잠실 축구장")
                .build();

        match.setId(1L);

        PostTagResponse postTag = new PostTagResponse(1L,match.getId(),'F',"beginner","adult");
        UserTagResponse userTag = new UserTagResponse(UUID.randomUUID(), "beginner", 'F', "adult");

        //when
        when(matchRepository.findById(any())).thenReturn(Optional.of(match));
        when(postServiceClient.getPostInfo(match.getId())).thenReturn(postTag);
        when(userServiceClient.getUserTagInfo(any())).thenReturn(userTag);



        //Then
        assertThrows(IllegalArgumentException.class, () -> {
                participationService.applyToMatch(match.getId(),userTag.userId());
        });
    }

    @Test
    @DisplayName("매치 참여 실패 테스트 : 태그 불일치")
    void applyToMatchFailByNotValid() {
        //Given
        Match match = Match.builder()
                .category("soccer")
                .currentAttendanceCount(5)
                .requiredAttendanceCount(6)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusDays(1))
                .leaderId(UUID.randomUUID())
                .place("잠실 축구장")
                .build();

        match.setId(1L);

        PostTagResponse postTag = new PostTagResponse(1L,match.getId(),'M',"beginner","adult");
        UserTagResponse userTag = new UserTagResponse(UUID.randomUUID(), "beginner", 'F', "adult");

        //when
        when(matchRepository.findById(any())).thenReturn(Optional.of(match));
        when(postServiceClient.getPostInfo(match.getId())).thenReturn(postTag);
        when(userServiceClient.getUserTagInfo(any())).thenReturn(userTag);

        //Then
        assertThrows(NotValidUserForMatchException.class, () -> {
            participationService.applyToMatch(match.getId(),userTag.userId());
        });
    }
}
