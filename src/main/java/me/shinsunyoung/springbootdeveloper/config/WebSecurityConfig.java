package me.shinsunyoung.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.shinsunyoung.springbootdeveloper.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userService;

    // 1. 스프링 시큐리티 기능 비활성화 (h2 와 정적 리소스)
    @Bean
    public WebSecurityCustomizer configure() { // void customize(WebSecurity web); 라는 추상메서드 하나만을 가진 Functional Interface 임
        return (web) -> web.ignoring() // 따라서 해당 추상메서드를 구현하는 것으로 추론
                .requestMatchers(toH2Console()) // org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console : h2 콘솔 경로(/h2-console/**)를 자동으로 지정하는 헬퍼
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

}
