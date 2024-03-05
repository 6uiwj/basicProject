package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

//UserDetailsService : 회원정보 조회 - 스프링 시큐리티 내에서 사용
//→MemberInfoService를 구현체로 만들 것임
//스프링 시큐리티에서 interface로 정의해줬으믈 우리는 구현체를 만들고 반환값만 정해주면 됨
@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;

    //회원 인증, 권한 체크 시 회원정보 조회할 때 사용
    //username은 아이디 또는 이메일(우선순위)
    //이메일이 있으면 그것을 쓰고, 이메일이 없으면 아이디로 조회(이를 한번에 다 하기 위해 optional을 씀)

    /**
     * 이메일로 조회하고, 없으면 아이디로 조회한 후, 없으면 예외를 던짐
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username)
                .orElseGet(() -> memberRepository.findByUserId(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username)));

        List<SimpleGrantedAuthority> authorities = null;
        //권한 가져오기
        List<Authorities> tmp = member.getAuthorities(); //권한 테이블 값 가져옴 -> enum상수이므로 이걸 grantedAuthority 형태로 바꿔야 함
        if(tmp != null) {
            //DB에서 가져온 상수 데이터를 스트림을 이용해 문자열 리스트 형태로 가공
           authorities = tmp.stream()
                    .map(s -> new SimpleGrantedAuthority(s.getAuthority().name()))
                    .toList(); //가져왔던 데이터에서 상수만 뽑아오고 문자로 반환
        }

        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getPassword())
                .member(member)  //추가적인 부분은 member에서 직접 조회
                .authorities(authorities)
                .build();
    }

}
