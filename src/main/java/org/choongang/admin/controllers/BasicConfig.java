package org.choongang.admin.controllers;

import lombok.Data;

//커맨드 객체
@Data
public class BasicConfig {
    private String siteTitle;
    private String siteDescription;
    private String siteKeywords;
    private int cssJsVersion;
    private String joinTerms;

}
