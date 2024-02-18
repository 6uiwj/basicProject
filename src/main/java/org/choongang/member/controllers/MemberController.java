package org.choongang.member.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//이 컨트롤러에서 발생한 에러가 에러페이지로 유입될 수 있게 아까 정의 해준 ExceptionProcessor interface 상속받음
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor //자동의존주입을 위해
public class MemberController implements ExceptionProcessor {
    private final Utils utils; //디바이스별 경로 설정을 위해

    //get방식으로 접속하면 회원가입 양식이 나오도록
    @GetMapping("/join")
    public String join() {


        //템플릿 연결
        return utils.tpl("member/join");
    }
    //회원가입 처리 - post방식 (join process)
    @PostMapping("/join")
    public String joinPs() {
        //회원가입 성공시 로그인페이지로 이동
        return "redirect:/member/login";
    }


    //로그인 처리는 스프링 시큐리티가 해주므로 로그인 양식만 정해주면 됨
    @GetMapping("/login")
    public String login() {
        return utils.tpl("member/login");
    }
}
