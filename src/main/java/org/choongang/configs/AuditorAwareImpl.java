package org.choongang.configs;

import org.choongang.member.service.MemberInfo;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

//추가/수정했을 때 넣어줄 사용자 정보를 무엇으로? 회원번호/ 이메일/ 사용자아이디 중
//-> 아이디로 해보자
//정보에 따라 자료형을 지네릭클래스로 지정
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

//securityContextHolder를 통해 회원정보를 가져와보자
    @Override
    public Optional<String> getCurrentAuditor() {

        //Optional형태로 만들어서 값을 넣어주자
        String userId = null;

        //회원정보와 Authentication객체 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //로그인 여부 체크
        // - 1. Authentication객체에 값이 있나 확인,
        // - 2. getPrincipal에 userDetails 구현체가 있나 확인

        /**
         * getPrincipal() 반환값
         * 로그인 상태 -> UserDetails 구현객체 가져옴(=MemberInfo)
         * 미로그인 상태 -> String형태로 "anonymousUser"가 나옴(문자열)
         */
        //값이 문자열이면 미로그인상태, 문자열이 아니면 로그인 상태
        //auth가 null이 아니고 MemberInfo의 구현체(혹은 UserDetails의)이면 로그인상태
        if (auth != null && auth.getPrincipal() instanceof MemberInfo) {
            MemberInfo memberInfo = (MemberInfo)auth.getPrincipal();
            userId = memberInfo.getUserId();

        }
        return Optional.ofNullable(userId); //ofNullable: null 값 허용
    }
}
