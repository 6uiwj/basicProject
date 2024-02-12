package org.choongang.controllers.member;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//멤버 컨트롤러
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final Utils utils;

    /**
     * 회원가입
     * @return
     */
    @GetMapping("/join")
    public String join() {

        //return "front/member/join";
        return utils.tpl("member/join");
    }
}
