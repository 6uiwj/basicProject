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

@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController implements ExceptionProcessor {
        @ModelAttribute("menuCode") //주메뉴코드... 하...^^
        public String getMenuCode() {
            return "member";
        }

        // 컨트롤러 내에서 값 공유.유지
        @ModelAttribute("subMenus")
        public List<MenuDetail> getSubMenus() {
            return Menu.getMenus("member");
        }

        @GetMapping
        public String list(Model model) {
            model.addAttribute("subMenuCode", "list"); //메뉴 ㅎ.뭔데..
            return "admin/member/list";
        }

}
