package org.choongang.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {
    //세션데이터를 직접 조회해서 바로 쓸 수 있도록
    //회원데이터와 로그인 여부를 여기서 체크해 볼 수 있도록 편의메서드 추가

    private final HttpSession session;


    //세션을 통해 회원 데이터 조회
    public Member getMember() {
        Member member = (Member)session.getAttribute("member");

        return member;
    }

    //로그인여부 체크
    public boolean isLogin() {
        //세션 데이터가 없으면 로그인상태X, 있으면 로그인 상태 O
        return getMember() != null;
    }


    //로그인 세션 비우기
    public static void clearLoginData(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("NotBlank_username");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("Global_error");
    }
}
