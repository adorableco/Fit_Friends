package com.example.match_service.controller;

import com.example.match_service.common.dto.CustomResponseBody;
import com.example.match_service.common.dto.StatusCode;
import com.example.match_service.common.resolver.userid.UserId;
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
    public ResponseEntity<CustomResponseBody<byte[]>> createQr(@UserId UUID userId, @PathVariable Long matchId) throws Exception {
        return ResponseEntity.ok(new CustomResponseBody<>(StatusCode.SUCCESS,"QR 생성에 성공하였습니다.",attendanceService.createQr(userId,matchId)));
    }

    @PostMapping("/attendances/{matchId}")
    public ResponseEntity<CustomResponseBody<Void>> qrToAttendance(@UserId UUID userId, @PathVariable Long matchId) throws Exception {
        attendanceService.qrToAttendance(userId,matchId);
        return ResponseEntity.ok(new CustomResponseBody<>(StatusCode.SUCCESS, "QR 출석에 성공하였습니다"));
    }
}
