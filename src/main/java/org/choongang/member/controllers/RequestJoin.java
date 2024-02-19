package org.choongang.member.controllers;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

//요청데이터를 처리할 커맨드 객체
@Data
public class RequestJoin {
    @NotBlank @Email //@Email: 이메일 형식을 검증할 수 있음
    private String email;

    @NotBlank @Size(min=6)
    private String userId;

    @NotBlank
    @Size(min=8)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String name;

    @AssertTrue
    private boolean agree; //약관 동의
}
