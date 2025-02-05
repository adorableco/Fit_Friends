package com.example.userservice.common.dto;

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

}