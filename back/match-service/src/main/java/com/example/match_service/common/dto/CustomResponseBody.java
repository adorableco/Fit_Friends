package com.example.match_service.common.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomResponseBody<T> {
    private String result;
    private int code;
    private String message;
    private T data;

    public CustomResponseBody(StatusCode statusCode, String message, T data) {
        this.result = statusCode.name();
        this.code = statusCode.getCode();
        this.message = message;
        this.data = data;
    }

    public CustomResponseBody(StatusCode statusCode, String message) {
        this.result = statusCode.name();
        this.code = statusCode.getCode();
        this.message = message;
    }
}
