package me.shinsunyoung.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.shinsunyoung.springbootdeveloper.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

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
                .requestMatchers(new AntPathRequestMatcher("/static/**")); // 정적 리소스 에 대한 시큐리티 기능 비활성화
    }
    
    // 2. 특정 HTTP 요청에 대한 웹 기반 보안 요청
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth // 3. 인증, 인가 설정
                        .requestMatchers( // 특정 요청과 일치하는 url 에 대한 엑세스를 설정
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/user")
                        ).permitAll() // 누구나 접근 가능토록 설정. 위 url 로 요청이 오면 인증/인가 없이도 접근 가능
                        .anyRequest().authenticated()
                        // anyRequest() : 상기 url 이외 요청에 대해 설정
                        // authenticated() : 별도 인가는 필요없으나 인증 성공 상태여야 접근 가능
                )
                .formLogin(formLogin -> formLogin // 4. 폼 기반 로그인 설정
                        .loginPage("/login") // 로그인 페이지 경로 설정
                        .defaultSuccessUrl("/articles") // 로그인 완료 시 이동 경로 설정
                )
                .logout(logout -> logout // 5. 로그아웃 설정
                        .logoutSuccessUrl("/login") // 로그아웃 완료 시 이동 경로 설정
                        .invalidateHttpSession(true) // 로그아웃 이후 세션 전체 삭제 여부 설정
                )
                .csrf(AbstractHttpConfigurer::disable) // 6. csrf 비활성화 (csrf 공격 방지 위해서는 활성화하는게 좋음)
                .build();
    }

    // 7. 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService); // 8. 사용자 정보 서비스 설정. 반드시 UserDetailsService 를 상속받은 클래스여야 함
        authProvider.setPasswordEncoder(bCryptPasswordEncoder); // 비밀번호 암호화위한 인코더 설정
        return new ProviderManager(authProvider);
    }
    
    // 9. 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
