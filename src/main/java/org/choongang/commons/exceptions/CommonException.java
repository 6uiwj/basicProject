package org.choongang.commons.exceptions;

import org.springframework.http.HttpStatus;
//모든 예외를 정의해 줄 클래스
public class CommonException extends RuntimeException {
    private HttpStatus status;

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    //모든 예외를 정의할 때 응답코드를 가지고 정의?
    public HttpStatus getStatus() {
        return status;
    }
}
