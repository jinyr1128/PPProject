package com.team.gameblog.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception {

    private HttpStatus httpStatus;

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    //글로벌 예외쪽에서 커스텀 예외 처리할경우 해당 커스텀 상태코드 가져올때
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
