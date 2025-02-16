package com.example.postservice.common.exception;


import com.example.postservice.common.dto.CustomResponseBody;
import com.example.postservice.common.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponseBody<Void>> handleException(Exception ex) {
        if (ex.getMessage() != null) {
            return ResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        }
        return ResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomResponseBody<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        if (ex.getMessage() != null) {
            return ResponseUtil.error(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ResponseUtil.error(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomResponseBody<Void>> UserNotFoundException(UserNotFoundException ex) {
        if (ex.getMessage() != null) {
            return ResponseUtil.error(HttpStatus.NOT_FOUND, ex.getMessage());

        }
        return ResponseUtil.error(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<CustomResponseBody<Void>> PostNotFoundException(PostNotFoundException ex) {
        if (ex.getMessage() != null) {
            return ResponseUtil.error(HttpStatus.NOT_FOUND, ex.getMessage());

        }
        return ResponseUtil.error(HttpStatus.NOT_FOUND);
    }
}