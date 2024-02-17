package org.choongang.commons.exceptions;

import org.springframework.http.HttpStatus;
//모든 예외를 정의해 줄 클래스
//사용자가 정의한 예외 클래스가 RuntimeException의 하위 클래스임을 나타냄
public class CommonException extends RuntimeException {
    private HttpStatus status; //HTTP 응답코드 저장
    //생성자
    public CommonException(String message, HttpStatus status) {
        super(message); //매개변수로 받은 message를 부모클래스인 RuntimeCxcption에 전달
        this.status = status;
    }

    //모든 예외를 정의할 때 응답코드를 가지고 정의?
    public HttpStatus getStatus() {
        return status;
    }
}
