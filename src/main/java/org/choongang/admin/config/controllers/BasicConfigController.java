package org.choongang.admin.config.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.admin.config.service.ConfigSaveService;
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

    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;

    //메뉴코드 추가
    @ModelAttribute("menuCode") //Model에 넣어서 전역 데이터로 유지(on 클래스 추가, 활성화)
    public String getMenuCode() {

        return "config";
    }

    //양식 보여주기
    //매개변수에 BasicConfig 이제 필요없으니 지우고, 직접 불러오자
    @GetMapping //메인 페이지니까 index로 하쟈
    public String index(Model model) {
        //가져오고, 만약 없으면 비어있는 커맨드 객체 생성
        BasicConfig config = infoService.get("basic", BasicConfig.class).orElseGet(BasicConfig::new);

        model.addAttribute("basicConfig", config);
        return "admin/config/basic";
    }

    //제출+저장하기
    @PostMapping
    public String save(BasicConfig config, Model model
    ){
        saveService.save("basic", config);
        //저장 후 저장하였다는 메시지 띄워주기
        model.addAttribute("message", "저장되었습니다.");
        return "admin/config/basic"; //페이지 이동 없이 동일한 템플릿 보여주기
    }

}
