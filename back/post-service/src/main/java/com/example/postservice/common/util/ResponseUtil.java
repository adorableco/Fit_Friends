package com.example.postservice.common.util;

import com.example.postservice.common.dto.CustomResponseBody;
import com.example.postservice.common.dto.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<CustomResponseBody<T>> success(T data) {
        return ResponseEntity.ok(new CustomResponseBody<>(StatusCode.SUCCESS,StatusCode.SUCCESS.getMessage(), data));
    }

    public static <T> ResponseEntity<CustomResponseBody<T>> success(T data, String message) {
        return ResponseEntity.ok(new CustomResponseBody<>(StatusCode.SUCCESS ,StatusCode.SUCCESS.getMessage(), data));
    }

    public static ResponseEntity<CustomResponseBody<Void>> error(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus).body(new CustomResponseBody<>(StatusCode.FAIL, message, null));
    }

    public static ResponseEntity<CustomResponseBody<Void>> error(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(new CustomResponseBody<>(StatusCode.FAIL, StatusCode.FAIL.getMessage(), null));
    }
}