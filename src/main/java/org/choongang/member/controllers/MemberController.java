package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

//이 컨트롤러에서 발생한 에러가 에러페이지로 유입될 수 있게 아까 정의 해준 ExceptionProcessor interface 상속받음
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor //자동의존주입을 위해
public class MemberController implements ExceptionProcessor {

    private final Utils utils; //디바이스별 경로 설정을 위해
    private final JoinService joinService;

    private final MemberUtil memberUtil; //회원정보 조회, 로그인 여부 체크위해

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

        //2차검증
        //joinValidator.validate(form, errors);
        joinService.process(form,errors);

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


    ////////회원 정보 조회//////////
    /* 방법1
    //로그인시 로그인한 회원정보 주입 -> ID 조회
    @ResponseBody //rest 방식으로 변경
    @GetMapping("/info")
    private void info(Principal principal) {
        String username = principal.getName();
        System.out.printf("username=%s%n", username);
    }

     */

    /* 방법2
    @AuthenticationPrincipal를 이용하면 UserInfo의 구현 객체를 바로 주입받을 수 있음
    //한계: 요청메서드 주입밖에 안됨
    @ResponseBody //rest 방식으로 변경
    @GetMapping("/info")
    private void info(@AuthenticationPrincipal MemberInfo memberInfo) {
        System.out.println(memberInfo);
    }

     */


    /*
    //방법3
    //요청 메서드에 주입하지 않고 사용하는 방법
    @ResponseBody
    @GetMapping("/info")
    public void info() {
        MemberInfo memberInfo = (MemberInfo) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        System.out.println(memberInfo);
    }

     */

    //로그인 했을 때에는 회원 정보를 보여주고, 로그인하지 않았을 때에는 미확인된 정보라고 알려주기
    @ResponseBody
    @GetMapping("/info")
    public void info() {
        if(memberUtil.isLogin()) { //로그인 상태
            Member member= memberUtil.getMember();
            System.out.println(member);
        } else {
            System.out.println("미로그인 상태");
        }
    }
}
