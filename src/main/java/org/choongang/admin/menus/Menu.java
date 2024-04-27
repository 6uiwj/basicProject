package org.choongang.admin.menus;

import java.util.*;
//따로 객체를 만들지 않고 직접 접근할 수 있도록 정적인 방법으로 가져올 수 있도록
//주 메뉴코드만 입력하면 그 메뉴에 해당하는 서브메뉴를 가져오도록 설정
// 주메뉴는 Map, subMenu는 List 이용

public class Menu {
    //MeneDtail = 서브메뉴
    private final static Map<String, List<MenuDetail>> menus;
    static {
        //메뉴는 바뀌지 않으므로 처음 로딩될 때 완성이 되도록 해줌
        //QnA 여기서 왜 HashMap을 사용할까?
        menus = new HashMap<>();

        //QnA 여기서 왜 Map의 값의 데이터를 바로 List형태로 넣지 않고 Arrays.asList를 통해서 넣을까?
        //주 메뉴 코드 입력(회원) - 서브메뉴 (회원목록, 회원권한 등...) 맵으로 한 이유...- 킷값:주 메뉴코드
        //Arrays.asList : 값을 나눠서 입력하면 고정된 크기의  리스트로 바꿔서 반환해 줌(수정 불가)
        menus.put("member", Arrays.asList( //asList: 서브메뉴
                new MenuDetail("list", "회원목록", "/admin/member"),
                new MenuDetail("authority", "회원권한", "/admin/member/authority")
        ) );
        //"board" : 주메뉴 / Arrays...( 보조메뉴)
        menus.put("board", Arrays.asList(
                new MenuDetail("list", "게시판목록", "/admin/board"),
                new MenuDetail("add", "게시판등록", "/admin/board/add"),
                new MenuDetail("posts", "게시글관리", "/admin/board/posts")
        ));
    }
    //code: 주메뉴코드
    public static List<MenuDetail> getMenus(String code) {
        return menus.get(code);
    }
}
