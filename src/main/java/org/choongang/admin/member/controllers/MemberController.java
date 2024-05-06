package org.choongang.admin.member.controllers;

import org.choongang.admin.menus.Menu;
import org.choongang.admin.menus.MenuDetail;
import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
//클래스이름 중복을 피하기 위해 컨트롤러 이름을 직접 정해줌
@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController implements ExceptionProcessor { //에러페이지 연동(ExceptionProcessor)

        /*메뉴 등 공통 데이터 유지할 때
             방법1: ControllerAdvice 이용(데이터가 많을 때)
             방법2: ModelAttribute(설정만 필요 시) */
        //주메뉴

    /**
     * 메뉴코드 값이 들어와 있으면 멤버라는 값을 찾아서 on 클래스를 추가
     * @return
     */
    @ModelAttribute("menuCode") //주메뉴코드... 하...^^
        public String getMenuCode() {
            return "member";
        }

        // 컨트롤러 내에서 값 공유.유지
        // 서브메뉴
        @ModelAttribute("subMenus") //subMenus라는 속성값이 있으면 템플릿에 출력
        public List<MenuDetail> getSubMenus() {
            return Menu.getMenus("member");
        }

    /**
     * subMenuCode를 "list"로 설정 -> Menus의 code와 비교하여
     * 일치하면 on 클래스를 추가해줌 (_submenu.html)
     * @param model
     * @return
     */
        @GetMapping
        public String list(Model model) {
            model.addAttribute("subMenuCode", "list"); //메뉴 ㅎ.뭔데..
            return "admin/member/list";
        }

}
