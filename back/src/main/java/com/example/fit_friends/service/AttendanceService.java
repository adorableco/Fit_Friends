package com.example.fit_friends.service;

import com.example.fit_friends.domain.Match;
import com.example.fit_friends.domain.Participation;
import com.example.fit_friends.domain.User;
import com.example.fit_friends.repository.MatchRepository;
import com.example.fit_friends.repository.ParticipationRespository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final MatchRepository matchRepository;
    private final ParticipationService participationService;

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

    public ResponseEntity<byte[]> createQr(User user, Long matchId) throws Exception {

        Match match = matchRepository.findById(matchId).get();

        int width = 200;
        int height = 200;

        long time = differenceTime(match);


        if (match.getUser().getUserId() == user.getUserId()) {

        Map<String, String> response = new HashMap<>();
        response.put("url","http://localhost:8080/api/attendance/"+matchId );

        BitMatrix encode = new MultiFormatWriter().encode(response.toString(), BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(encode, "PNG", out);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(out.toByteArray());

        }else if (time > 30 * 60000 || time < -10 * 60000) {
                return ResponseEntity.badRequest().body("출석 체크 가능 시간이 아님".getBytes(StandardCharsets.UTF_8));
            }
        else {
            return ResponseEntity.badRequest()
                    .body("경기 리더가 아님".getBytes(StandardCharsets.UTF_8));
        }
    }

    public ResponseEntity<String> qrToAttendance(Long matchId, String userEmail) throws Exception {
        Match match = matchRepository.findById(matchId).get();
        Optional<Participation> participation = participationService.findByMatchAndUser(matchId, userEmail);

        long time = differenceTime(match);


        if (time > 30 * 60000 || time < -10 * 60000) {
            return ResponseEntity.badRequest().body("출석 체크 가능 시간이 아님");

        }
         else if (participation.get().isAttendance()) {
            return ResponseEntity.badRequest().body("이미 출석 체크 완료");
        } else {
            return participationService.checkAttendance(matchId, userEmail);
        }
    }
}
