package org.choongang.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.choongang.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//범위 설정 -> 일반 컨트롤러에 해당하는 공통적인 부분을 수행할 수 있는 컨트롤러
@ControllerAdvice("org.choongang.controllers") //이 범위에만 해당
public class CommonController {
    //모든 예외가 발생하면 다여기로 유입되도록
    //응답코드도 가져올 수 있도록 HttpServletResponse객체 가져오기
    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, HttpServletResponse response, Model model) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; //일단 500이 기본값

        //응답코드는 CommonException이 있을 때에만 존재
        if (e instanceof CommonException) {//얘가 CommonException으로부터 온 예외인가?

        }
        return "error/common";
    }
}
