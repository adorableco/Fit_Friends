package com.example.fit_friends.service;

import com.example.fit_friends.domain.Match;
import com.example.fit_friends.domain.Participation;
import com.example.fit_friends.domain.Tag;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.repository.MatchRepository;
import com.example.fit_friends.repository.ParticipationRepository;
import com.example.fit_friends.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ParticipationService {

    @Autowired
    private final MatchRepository matchRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ParticipationRepository participationRepository;

    @Transactional
    public Optional<Participation> findByMatchAndUser(Long matchId, String userEmail) {
        Match match = matchRepository.findById(matchId).get();
        User user = userRepository.findByEmail(userEmail).get();
        return participationRepository.findByMatchAndUser(match, user);
    }

    @Transactional
    public List<Participation> findByUser(User user) {
        modifyParticipationStatus(user);
        List<Participation> participationList = participationRepository.findByUser(user);

        return participationList;
    }

    public Float countMyPresentedMatches(Long userId) {
        return participationRepository.countMyPresentedMatches(userId);
    }

    public Float countMyEndMatches(LocalDateTime now, Long userId) {
        return participationRepository.countMyEndMatches(now, userId);
    }

    @Transactional
    public void modifyParticipationStatus(User user){
        List<Participation> participationList = participationRepository.findByUser(user);
        participationList.stream().forEach(participation -> {participation.modifyParticipationStatus();});
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
            participationRepository.save(build);
            match.setCurrentHeadCnt(match.getCurrentHeadCnt()+1);
            return "참가 신청 완료";
        }
    }

    @Transactional
    public ResponseEntity<String> checkAttendance(Long matchId, String userEmail) {
        try {
            Match match = matchRepository.findById(matchId).get();
            User user = userRepository.findByEmail(userEmail).get();
            Participation participation = participationRepository.findByMatchAndUser(match, user).get();
            participation.setAttendance(true);
            int attendanceCount = match.getAttendanceCnt();
            match.setAttendanceCnt(attendanceCount + 1);
            return ResponseEntity.ok().body("출석 체크 완료");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("출석 체크 실패");
        }
    }
}
