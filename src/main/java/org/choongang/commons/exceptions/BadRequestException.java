package org.choongang.commons.exceptions;

import org.springframework.http.HttpStatus;

//404에러 정의?
public class BadRequestException extends CommonException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
