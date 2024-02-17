package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.choongang.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

//일반적인 컨트롤러 에러를 다룰 것임
public interface ExceptionProcessor {
    //모든 예외가 발생하면 다 여기로 유입되도록
    //응답코드도 가져올 수 있도록 HttpServletResponse객체 가져오기
    @ExceptionHandler(Exception.class)
    default String errorHandler(Exception e, HttpServletResponse response, HttpServletRequest request, Model model) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; //일단 모든 예외는 500이 기본값으로 설정

        //응답코드는 CommonException이 발생했을 때에만 존재 따라서 commonException형태만 가져오자
        if (e instanceof CommonException) {//얘가 CommonException으로부터 온 예외인가? 체크
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus(); //응답코드는 각 예외에 따라서 다르게 발생
        }

        response.setStatus(status.value()); //응답코드 설정
        e.printStackTrace();//콘솔에 에러 출력

        //동일하게 템플릿을 공유하기 위해 동일한 이름으로 넣어 줌
        model.addAttribute("status", status.value()); //에러 상태코드 저장
        model.addAttribute("path", request.getRequestURI() ); //현재 경로 저장
        model.addAttribute("method", request.getMethod()); //요청 메서드 저장
        model.addAttribute("message", e.getMessage());
        return "error/common";
    }
}
