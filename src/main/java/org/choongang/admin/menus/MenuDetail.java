package org.choongang.admin.menus;

public record MenuDetail (
    String code, //하위 메뉴 코드 - 스타일을 입힐 수 있도록
    String name, //메뉴 명
    String url // 메뉴 URL
) {

    }
