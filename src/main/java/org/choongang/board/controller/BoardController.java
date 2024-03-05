package org.choongang.board.controller;

import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.BoardData;
import org.choongang.board.repositories.BoardDataRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardDataRepository boardDataRepository;

    @ResponseBody
    @GetMapping("/test")
    public void test() {
        /*
        BoardData data = new BoardData();
        //로그인한 회원정보는 알아서 들어갈 것임
        data.setSubject("제목");
        data.setContent("내용");
        boardDataRepository.saveAndFlush(data);
         */
        BoardData data = boardDataRepository.findById(1L).orElse(null);
        data.setSubject("(수정)제목");
        boardDataRepository.flush();
    }

    //로그인 했을 때에만 접근 가능 (메서드 호출 전)
    /*
    @ResponseBody //반환값이 없어도(템플릿 연결이 없어도 오류가 안뜨도록
    @GetMapping("/test2")
    @PreAuthorize("isAuthenticated()")
    public void test2() {
        System.out.println("test2!");
    }

     */
    //관리자 권한이 있을 때에만 접근 가능
    /*
    @ResponseBody //반환값이 없어도(템플릿 연결이 없어도 오류가 안뜨도록
    @GetMapping("/test2")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void test2() {
        System.out.println("test2!");
    }

     */
    /*
    //권한만 체크하고 싶을 때
    @ResponseBody //반환값이 없어도(템플릿 연결이 없어도 오류가 안뜨도록
    @GetMapping("/test2")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public void test2() {
        System.out.println("test2!");
    }


     */
}
