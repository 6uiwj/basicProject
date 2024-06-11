package org.choongang.admin.config.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//관리자 페이지 기본설정 컨트롤러
@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class BasicConfigController implements ExceptionProcessor { //에러페이지 연동

    //메뉴코드 추가
    @ModelAttribute("menuCode") //Model에 넣어서 전역 데이터로 유지(on 클래스 추가, 활성화)
    public String getMenuCode() {

        return "config";
    }

    //양식 보여주기
    @GetMapping //메인 페이지니까 index로 하쟈
    public String index(@ModelAttribute BasicConfig config, Model model
    ) {
        return "admin/config/basic";
    }

    //제출+저장하기
    @PostMapping
    public String save(BasicConfig config, Model model
    ){
        return "admin/config/basic"; //페이지 이동 없이 동일한 템플릿 보여주기
    }

}
