package org.choongang.configs;

import org.choongang.member.service.LoginFailureHandler;
import org.choongang.member.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//스프링 시큐리티 설정 파일(스프링 시큐리티에 관한 설정은 전부 여기에 해줄 것임 )
@Configuration
public class SecurityConfig {
//    SecurityFilterChain : 대부분의 설정은 이걸로 해줄것임

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /* 인증설정 S - 로그인 */
        http.formLogin(f -> { //로그인 설정 : formLogin 인터페이스 이용(DSL 형태. 람다식..)
           f.loginPage("/member/login")//로그인 처리 페이지 - 바뀔 수 있는 부분 이름 명시해서 설정
                   .usernameParameter("username") //login 템플릿의 아이디 name 값
                   .passwordParameter("password") //login 템플릿의 비밀번호 name 값
                   .successHandler(new LoginSuccessHandler()) //로그인 성공 후 상세설정
                   .failureHandler(new LoginFailureHandler());

        });
        /* 인증설정 E - 로그인 */

        return http.build(); //기존 시큐리티 로그인 페이지 무력화
    }

    /**
     * BCryipt를 이용한 비밀번호 해시화 - 스프링시큐리티 내장 기능 이용
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
