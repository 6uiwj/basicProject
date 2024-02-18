package org.choongang.member.service;

import lombok.Builder;
import lombok.Data;
import org.choongang.member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
//스프링 시큐리티에서 이미 구현되어 있으므로 값만 넣어주면 됨
//회원정보를 넣어주게 되면 스프링 시큐리티 내에서 필요한 정보를 알아서 조회하여 사용 
@Data
@Builder
public class MemberInfo implements UserDetails {
    //기본정보 - 외부에서 셋팅하면(값을 넘기면) 쓸 수 있도록
    private String email;
    private String userId;
    private String password;

    private Member member; //추가정보가 필요할 시, 여기서 가져 옴

    //Collection -> set, list...등이 될 수 있음
    //아래의 getAuthorities에서 받아올 값 설정
    private Collection<? extends GrantedAuthority> authorities;

    //특정 권한에 대한 인가(괸리자 or 멤버 or ...)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    //비밀번호 - 로그인시 이용
    @Override
    public String getPassword() {
        return null;
    }

    //ID - 로그인시 이용
    @Override
    public String getUsername() {
        return null;
    }

    //계정이 만료되지 않은 상태인가?
    @Override
    public boolean isAccountNonExpired() {
        return true; //임시
    }

    //계정이 잠겨있지 않는가?
    @Override
    public boolean isAccountNonLocked() {
        return true; //임시
    }

    //비밀번호가 만료되지 않은 상태인가?
    @Override
    public boolean isCredentialsNonExpired() {
        return true; //임시
    }

    //탈퇴여부? false: 탈퇴
    @Override
    public boolean isEnabled() {
        return true; //임시
    }
    //추상메서드로 정의된 부분을 구현해주자.

}
