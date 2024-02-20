package org.choongang.member.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 커맨드 객체 내에서 어노에티션으로 1차 검증,
 * 추가적인 검증이 필요한 부분은 Validator를 통해 2차 검증
 */
@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator {
    
    private final MemberRepository memberRepository;
    /**
     * 검증 대상을 한정하는 메서드
     * 매개변수로 받은 class가
     * RequestJoin과 일치 or RquestJoin의 하위 클래스인지를 판별
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        //검증 대상은 requestJoin 커맨드 객체 하나로 정해져 있으므로 반환값에 바로설정
        return clazz.isAssignableFrom(RequestJoin.class);
    }

    //검증
    @Override
    public void validate(Object target, Errors errors) {
        /**
         * 1. 이메일, 아이디 중복 여부 체크
         * 2. 비밀번호 복잡성 체크 - 대소문자 1개 각각 포함, 숫자 1개 이상 포함, 특수문자 1개 이상 포함
         * 3. 비밀번호, 비밀번호 확인 일치 여부 체크
         */

        RequestJoin form = (RequestJoin)target;
        String email = form.getEmail();
        String userId = form.getUserId();
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();

        //1. 이메일, 아이디 중복 여부 체크 - 레포지토리 의존성 추가,
        // 레포지토리에 존재 여부를 조회할 수 있는 메서드추가
        //이메일이 있고, 이메일이 이미 가입되어 있는가?
        if(StringUtils.hasText(email)&&memberRepository.existsByEmail(email)){ //참이 되면 안됨
                errors.rejectValue("email", "Duplicated");
        }

        if (StringUtils.hasText(userId) && memberRepository.existsByUserId(userId)) {
            errors.rejectValue("userId", "Duplicated");
        }


        //2. 비밀번호 복잡성 체크 - 대소문자 1개 각각 포함, 숫자 1개 이상 포함, 특수문자 1개 이상 포함


        //3. 비밀번호, 비밀번호 확인 일치 여부 체크

    }
}
