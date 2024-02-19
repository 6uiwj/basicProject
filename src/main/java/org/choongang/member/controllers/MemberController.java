package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//이 컨트롤러에서 발생한 에러가 에러페이지로 유입될 수 있게 아까 정의 해준 ExceptionProcessor interface 상속받음
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor //자동의존주입을 위해
public class MemberController implements ExceptionProcessor {
    private final Utils utils; //디바이스별 경로 설정을 위해

    //get방식으로 접속하면 회원가입 양식이 나오도록
    @GetMapping("/join") //RequestJoin
    public String join(@ModelAttribute RequestJoin form) {


        //템플릿 연결
        return utils.tpl("member/join");
    }

    //회원가입 처리 - post방식 (join process)
    //검증 필요 @Valid
    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors  errors) {
        //회원가입 성공시 로그인페이지로 이동
        //에러가 발생하면 errors가 참 -> 다시 회원가입 템플릿을 보여주고 에러메시지 출력
        if(errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        return "redirect:/member/login";
    }


    //로그인 처리는 스프링 시큐리티가 해주므로 로그인 양식만 정해주면 됨
    @GetMapping("/login")
    public String login() {
        return utils.tpl("member/login");
    }
}
