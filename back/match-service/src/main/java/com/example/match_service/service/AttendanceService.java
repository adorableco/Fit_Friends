package com.example.match_service.service;

import com.example.match_service.common.exception.NotMatchLeaderException;
import com.example.match_service.domain.Match;
import com.example.match_service.domain.Participation;
import com.example.match_service.repository.MatchRepository;
import com.example.match_service.repository.ParticipationRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final MatchRepository matchRepository;
    private final ParticipationRepository participationRepository;

    private long differenceTime(Match match) throws ParseException {
        String now= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
        String matchTime= match.getStartTime().toString();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = format.parse(now);
        Date d2 = format.parse(matchTime);
        long sec = (d2.getTime() - d1.getTime()) / 1000;
        long min = (d2.getTime() - d1.getTime()) / 60000;
        long hour = (d2.getTime() - d1.getTime()) / 3600000;
        long days = sec / (24*60*60);

        return sec + min + hour + days;
    }

    public byte[] createQr(UUID id, Long matchId) throws Exception {

        Match match = matchRepository.findById(matchId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 match 정보입니다."));

        int width = 200;
        int height = 200;

        long time = differenceTime(match);
        System.out.println("time = " + time);

        if (match.getLeaderId() != id){
            throw new NotMatchLeaderException();
        }
        else if (time > 30 * 40 || time < -10 * 40) {
            throw new IllegalArgumentException("출석 체크 가능한 시간이 아닙니다.");
        }

        String url = "http://3.39.127.227:8081/api/attendance/"+matchId;

        //qr 생성
        BitMatrix encode = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(encode, "PNG", out); // PNG 형식으로 출력
            return out.toByteArray();
        }
    }

    @Transactional
    public void qrToAttendance(UUID userId, Long matchId) throws Exception {
        Participation participation = participationRepository.findByMatchIdAndUserId(matchId,userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매치에 참여하는 유저가 아닙니다."));

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매치가 존재하지 않습니다."));

        long time = differenceTime(match);


        if (time > 30 * 40 || time < -10 * 40 ) {
            throw new IllegalArgumentException("출석 체크 가능 시간이 아닙니다.");
        }
        else if (participation.isAttendance()) {
            throw new IllegalArgumentException("이미 출석 체크 완료하였습니다.");
        }
        else {
            //출석 체크
            updateAttendance(matchId, userId);
        }
    }

    private void updateAttendance(Long matchId, UUID userId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 매치 정보가 없습니다."));
        Participation participation = participationRepository.findByMatchIdAndUserId(matchId, userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 참여 정보가 없습니다."));

        participation.updateAttendanceStatus(true);
        match.updateCurrentCnt();
    }
}
