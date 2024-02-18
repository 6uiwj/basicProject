package org.choongang.member.service;

import org.choongang.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

//회원이 없을 때 예외를 처리해줄 메서드
public class MemberNotFoundException extends CommonException {
    //메시지와 응답코드가 고정되므로 고정문구 설정해줌
    public MemberNotFoundException() {
        super("등록된 회원이 아닙니다.", HttpStatus.NOT_FOUND);
    }
}
