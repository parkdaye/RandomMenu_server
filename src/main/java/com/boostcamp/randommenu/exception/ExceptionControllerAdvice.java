package com.boostcamp.randommenu.exception;

import com.boostcamp.randommenu.dto.DefaultRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionControllerAdvice {

    //서비스에서 null return할 경우
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleServerException(Exception e) {
        DefaultRes serverExceptionRes = DefaultRes.builder()
                .message(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(serverExceptionRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //requestBody가 비어있일 경우
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity handleValueEmptyException(MethodArgumentNotValidException e) {
        DefaultRes valueEmptyExceptionRes = DefaultRes.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(valueEmptyExceptionRes, HttpStatus.BAD_REQUEST);
    }


}
