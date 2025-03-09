package com.example.match_service.common.exception;

public class NotValidUserForMatchException extends RuntimeException {
    static final String message = "매치 참여 조건을 충족하지 않습니다.";
    public NotValidUserForMatchException() {
        super(message);
    }
}
