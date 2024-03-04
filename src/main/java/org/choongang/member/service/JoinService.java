package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.Authority;
import org.choongang.member.controllers.JoinValidator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.AuthoritiesRepository;
import org.choongang.member.repositories.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor //의존성이 필요하니까
@Transactional
public class JoinService {

    private final MemberRepository memberRepository; //DB작업
    private final AuthoritiesRepository authoritiesRepository; //회원가입시 기본 권한 부여 위해
    private final JoinValidator joinValidator; //검증
    private final PasswordEncoder encoder; //비밀번호 해시화

    //검증, 커맨드객체 변환 후 엔티티에 반영하여 DB처리
    public void process(RequestJoin form, Errors errors) {
        joinValidator.validate(form, errors);
        //검증이 실패하면 다음 절차를 시행하지 않고 중단
        if ( errors.hasErrors() ) {
            return;
        }

        /**
         * modelMapper : 동일한 명칭의 다른 자료형이긴 하지만
         * getter, seter의 동일한 패턴이 있으면 알아서 바꿔서 넣어줌
         *  - 양식에 항목이 많은 경우 알아서 값이 변환될 수 있게 이용
         *
         * RequestJoin 커맨드 객체로 받은 데이터를 Member 엔티티 필드로 변환
         */

        //가입처리
        //1. 비밀번호 해시화(BCrypt 이용)
        String hash = encoder.encode(form.getPassword());

        //두개의 클래스를 비교해보고 데이터 치환
        Member member = new ModelMapper().map(form, Member.class);
        /**
         * <ModelMapper가 오류 뜰 때>
         *     Member member = new Member();
         *     member.setEmail(form.getEmail());
         *     member.setName(form.getName());
         *     member.setPassword(hash);
         *     member.setUserId(form.getUserId());
         * */

        //비밀번호는 해시화한 형태로 바뀌어 있으므로 직접 넣어줌
        member.setPassword(hash);
        //DB에저장 (회원가입 완료)
        process(member);

        //회원 가입 시 일반 사용자 권한 부여(USER)
        Authorities authorities = new Authorities();
        authorities.setMember(member);
        authorities.setAuthority(Authority.USER); //USER 권한 부여
        authoritiesRepository.saveAndFlush(authorities);
    }

    //회원가입 처리 - 엔티티를 가지고 DB에 저장
    public void process(Member member){
        //저장처리
        memberRepository.saveAndFlush(member);
    }
}
