package com.example.fit_friends.service;

import com.example.fit_friends.domain.Match;
import com.example.fit_friends.domain.Participation;
import com.example.fit_friends.domain.Tag;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.repository.MatchRepository;
import com.example.fit_friends.repository.ParticipationRespository;
import com.example.fit_friends.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ParticipationService {
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final ParticipationRespository participationRespository;

    public Optional<Participation> findByMatchAndUser(Long matchId, String userEmail) {
        Match match = matchRepository.findById(matchId).get();
        User user = userRepository.findByEmail(userEmail).get();
        return participationRespository.findByMatchAndUser(match, user);
    }
    
    @Transactional
    public String save(Long matchId, User user){
        Match match = matchRepository.findById(matchId).get();
        Tag tag = match.getTag();

        if (!(tag.getAgeType().equals(user.getAge())) || (tag.getGenderType() != user.getGender()) || !(tag.getLevelType().equals(user.getLevel()))
        ){

           return "참가 조건 미충족" ;
        }else if (match.getCurrentHeadCnt() == match.getHeadCnt()) {
            return "참가 인원 마감";
        }
        else if (matchRepository.findDuplicateMatch(user.getUserId(),match.getStartTime(),match.getEndTime()) > 0) {
            System.out.println("num = " + matchRepository.findDuplicateMatch(user.getUserId(),match.getStartTime(),match.getEndTime()));
            return "시간이 겹치는 신청 매치 존재" ;
        } else{
            Participation build = Participation.builder()
                    .user(user)
                    .match(match)
                    .build();
            participationRespository.save(build);
            match.setCurrentHeadCnt(match.getCurrentHeadCnt()+1);
            return "참가 신청 완료";
        }
    }

    @Transactional
    public ResponseEntity<String> checkAttendance(Long matchId, String userEmail) {
        try {
            Match match = matchRepository.findById(matchId).get();
            User user = userRepository.findByEmail(userEmail).get();
            Participation participation = participationRespository.findByMatchAndUser(match, user).get();
            participation.setAttendance(true);
            int attendanceCount = match.getAttendanceCnt();
            match.setAttendanceCnt(attendanceCount + 1);
            return ResponseEntity.ok().body("출석 체크 완료");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("출석 체크 실패");
        }
    }
}
