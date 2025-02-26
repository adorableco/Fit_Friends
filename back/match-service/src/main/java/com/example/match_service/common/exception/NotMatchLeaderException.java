package com.example.match_service.common.exception;

public class NotMatchLeaderException extends RuntimeException{
    static final String message = "해당 기능은 방장만 가능합니다.";
    public NotMatchLeaderException() {
        super(message);
    }
}
