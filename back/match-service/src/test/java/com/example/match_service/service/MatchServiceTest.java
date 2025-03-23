package com.example.match_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.match_service.client.dto.MatchCreateRequest;
import com.example.match_service.domain.Match;
import com.example.match_service.repository.MatchRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {
    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    @Test
    @DisplayName("match 생성 테스트")
     void createMatch_ShouldSaveMatchAndReturnId() {
        // Given
        UUID userId = UUID.randomUUID();
        MatchCreateRequest request = new MatchCreateRequest(userId,"Soccer", 5, "Stadium",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2));

        Match match = Match.builder()
                .leaderId(userId)
                .requiredAttendanceCount(request.headCnt())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .place(request.place())
                .category(request.category())
                .build();

        match.setId(1L);

        when(matchRepository.save(any(Match.class))).thenReturn(match);

        // When
        Long matchId = matchService.createMatch(new MatchCreateRequest(
                userId,"축구", 10, "서울", LocalDateTime.now(), LocalDateTime.now().plusHours(2)
        ));

        // Then
        assertNotNull(matchId);
        assertEquals(match.getId(), matchId);
        System.out.println("Returned matchId: " + matchId);
    }
}
