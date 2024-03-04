package org.choongang.member.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    //Authentication : 로그인 회원정보가 담겨있는 객체
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //로그인 성공시에도 로그인 페이지로 돌아오면 검증 메시지가 그대로 노출되므로
        //성공시에 메시지 데이터를 비워주자.
        HttpSession session = request.getSession();
        MemberUtil.clearLoginData(session);


        /* 회원 정보 조회 편의 구현 - session에 저장 */

        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        Member member = memberInfo.getMember();
        session.setAttribute("member", member);
        //메번 getAttribute를 통해 가져오기 귀찮으므로
        // memberutil에 회원 데이터를 조회할 수 있는 기능 정의 , 로그인 여부 체크메서드(member session값 통해)



        String redirectURL = request.getParameter("redirectURL");
        //로그인 후 값이 없는 경우 메인페이지로 이동
        redirectURL = StringUtils.hasText(redirectURL) ? redirectURL : "/";

        response.sendRedirect(request.getContextPath() + redirectURL);
    }
}
