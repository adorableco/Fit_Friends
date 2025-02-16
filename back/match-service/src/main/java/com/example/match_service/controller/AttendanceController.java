package com.example.match_service.controller;

import com.example.match_service.common.dto.CustomResponseBody;
import com.example.match_service.common.dto.StatusCode;
import com.example.match_service.service.AttendanceService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * qr 생성 및, qr 출석체크 Controller
 */
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins="http://localhost:19006", allowedHeaders = "*")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @GetMapping("/attendances/{matchId}")
    public ResponseEntity<CustomResponseBody<byte[]>> createQr(@PathVariable Long matchId) throws Exception {
        //TODO 헤더로부터 userID 추출
        //우선은 임시값 설정
        UUID userId = UUID.fromString("4d68c186-5ae4-4cde-a9c8-549c71f46478");
        return ResponseEntity.ok(new CustomResponseBody<>(StatusCode.SUCCESS,"QR 생성에 성공하였습니다.",attendanceService.createQr(userId,matchId)));
    }

    @PostMapping("/attendances/{matchId}")
    public ResponseEntity<CustomResponseBody<Void>> qrToAttendance(@PathVariable Long matchId) throws Exception {
        UUID userId = UUID.fromString("4d68c186-5ae4-4cde-a9c8-549c71f46478");
        attendanceService.qrToAttendance(userId,matchId);
        return ResponseEntity.ok(new CustomResponseBody<>(StatusCode.SUCCESS, "QR 출석에 성공하였습니다"));
    }
}
