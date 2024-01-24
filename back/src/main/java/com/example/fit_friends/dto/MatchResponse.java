package com.example.fit_friends.dto;

import com.example.fit_friends.domain.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
public class MatchResponse {
    private Long matchId;
    private int currentHeadCnt;
    private int headCnt;
    private String place;
    private Date startTime;
    private Date endTime;

    public MatchResponse(Match match) {
        this.matchId = match.getMatchId();
        this.currentHeadCnt = match.getCurrentHeadCnt();
        this.headCnt = match.getHeadCnt();
        this.place = match.getPlace();
        this.startTime = match.getStartTime();
        this.endTime = match.getEndTime();
    }

    public Match toEntity() {
        return Match.builder()
                .matchId(matchId)
                .currentHeadCnt(currentHeadCnt)
                .headCnt(headCnt)
                .place(place)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
