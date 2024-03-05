package org.choongang.configs;

import jakarta.servlet.http.HttpServletResponse;
import org.choongang.member.service.LoginFailureHandler;
import org.choongang.member.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//스프링 시큐리티 설정 파일(스프링 시큐리티에 관한 설정은 전부 여기에 해줄 것임 )
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
//    SecurityFilterChain : 대부분의 설정은 이걸로 해줄것임

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /* 인증설정 S - 로그인, 로그아웃 */

        http.formLogin(f -> { //로그인 설정 : formLogin 인터페이스 이용(DSL 형태. 람다식..)
           f.loginPage("/member/login")//로그인 처리 페이지 - 바뀔 수 있는 부분 이름 명시해서 설정
                   .usernameParameter("username") //login 템플릿의 아이디 name 값
                   .passwordParameter("password") //login 템플릿의 비밀번호 name 값
                   .successHandler(new LoginSuccessHandler()) //로그인 성공 후 상세설정
                   .failureHandler(new LoginFailureHandler());

        });

        //로그아웃으로 설정할 주소를 설정하고, 로그아웃 후 이동할 주소 설정 (혹은 추가기능 필요 시 LogoutHandler 이용 )
        http.logout(c -> {
            c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/member/login");//로그아웃 이후 이동할 페이지(로그인 페이지로 이동)
        });

        /* 인증설정 E - 로그인, 로그아웃 */



        /* 인가 설정 S - 접근 통제 */
        // hasAuthority(..) hasAnyAuthority(...), hasRole, hasAnyRole
        // ROLE_롤명칭
        // hasAuthority('ADMIN')
        // ROLE_ADMIN -> hasAuthority('ROLE_ADMIN')
        // hasRole('ADMIN')
        http.authorizeHttpRequests(c -> {
            //회원전용
            c.requestMatchers("/mypage/**").authenticated() //마이페이지를 포함한 모든 하위 경로
                    //.requestMatchers("/admin/**")
                   //.hasAnyAuthority("ADMIN", "MANAGER") //관리자,부관리자만 admin페이지 접근 가능
                    .anyRequest().permitAll(); //그 이외의 페이지는 모두 접근 가능
        });



        //접근하고 있는 주소에 따라 예외 설정
        //http.exceptionHandling(c -> {
            //권한없는 페이지에 접근했을 때 여기로 유입
            //new AuthenticationEntryPoint()가 회색이야! 쓰지말라는거....
            //얘는 내장메서드가 하나다..람다를 쓰라는 거래....
            /*
            c.authenticationEntryPoint(new AuthenticationEntryPoint() {
                @Override
                public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                }
             */

        //람다로 정의
            http.exceptionHandling(c -> {
                c.authenticationEntryPoint((req, res, e) -> {
                    String URL = req.getRequestURI(); //주소를 확인해보고 관리자이면 401, 아니면 로그인 페이지로 이동
                    if(URL.indexOf("/admin") != -1) { //관리자 페이지
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED); //401내보내기
                    } else { //회원전용 페이지
                        res.sendRedirect(req.getContextPath() + "/member/login");
                    }
            });
        });
        /* 인가 설정 E - 접근 통제 */

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
