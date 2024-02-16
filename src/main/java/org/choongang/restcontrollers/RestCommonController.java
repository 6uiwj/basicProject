package org.choongang.restcontrollers;

import org.choongang.commons.exceptions.CommonException;
import org.choongang.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//레스트방식의 공통 에러처리
//예외가 발생하며 유입될 수 있게 만들 것
//형식은 json형식으로 데이터 통일


@RestControllerAdvice("org.choongang.restcontrollers") //restcontrollers에 있는 모든 컨트롤러 범위
public class RestCommonController {
    //HTTP 응답을 나타내는 클래스
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData<Object>> errorHandler(Exception e) {
        //공통 응답코드는 500으로 지정
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; //500

        //발생한 에러가 CommonException의 객체이면 상태코드를 갖온다.
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException)e;
            status = commonException.getStatus();
        }

        //응답코드는 reponseEntity를 통해서 가져오고 JSON 데이터를 만든다?
        JSONData<Object> data = new JSONData<>();
        data.setSuccess(false); //실패시
        data.setStatus(status); //상태코드가 바뀌므로 바꿔준다.
        data.setMessage(e.getMessage()); //메시지도 ..

        e.printStackTrace(); //콘솔에 에러 출력

        //응답코드와 함꼐 바디를 통해 데이터 출력 - JSON 형태로 출력
        return ResponseEntity.status(status).body(data);
    }

}
