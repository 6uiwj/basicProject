package org.choongang.admin.config.controllers;

import lombok.Data;

//관리자페이지 기본설정 커맨드 객체
@Data
public class BasicConfig {
    private String siteTitle; //사이트 제목
    private String siteDescription; //사이트 설명
    private String siteKeywords; //사이트 키워드
    private int cssJsVersion; //프론트 버전
    private String joinTerms; //회원정보 유지 기간

}
