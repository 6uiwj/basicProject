package org.choongang.admin.controllers;

import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//관리자페이지 컨트롤러
@Controller("adminMainController")
@RequestMapping("/admin")
public class MainController implements ExceptionProcessor {
    @GetMapping
    public String index() {
        return "admin/main/index";
    } //관리자페이지 메인 템플릿
}
