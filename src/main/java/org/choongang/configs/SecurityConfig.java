package org.choongang.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//스프링 시큐리티 설정 파일(스프링 시큐리티에 관한 설정은 전부 여기에 해줄 것임 )
@Configuration
public class SecurityConfig {
//    SecurityFilterChain : 대부분의 설정은 이걸로 해줄것임

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.build(); //기존 시큐리티 로그인 페이지 무력화
    }
}
