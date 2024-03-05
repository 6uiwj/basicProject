package org.choongang.admin.config.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.controllers.BasicConfig;
import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class BasicConfigController implements ExceptionProcessor {

    //해당 메뉴가 on class가 추가되고 활성화된다구..?
    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "config";
    }

    //양식보여주기
    @GetMapping
    public String index(@ModelAttribute BasicConfig config, Model model
    ) {
        return "admin/config/basic";
    }

    //제출하기
    @PostMapping
    public String save(BasicConfig config, Model model
    ){
        return "admin/config/basic";
    }

}
