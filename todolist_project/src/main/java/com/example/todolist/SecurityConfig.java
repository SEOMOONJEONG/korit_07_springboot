package com.example.todolist;

import com.example.todolist.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
// 이 클래스가 스프링의 설정 클래스임을 나타냅니다.
// 스프링 컨테이너에 빈으로 등록되고 설정 정보로 사용됩니다.

@EnableWebSecurity
// 스프링 시큐리티의 웹 보안을 활성화합니다.
// 이 어노테이션이 있어야 웹 보안 관련 설정이 적용됩니다.

public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    // 사용자 인증 정보를 불러오는 커스텀 서비스 (UserDetailsService 구현체)

    private final AuthencationFilter authencationFilter;
    // 인증 필터 (JWT 같은 토큰 인증 등을 처리하는 커스텀 필터)

    private final AuthEntryPoint exceptionHandler;
    // 인증 실패 시 동작하는 핸들러 (Unauthorized 응답 처리)

    // 생성자 주입: 스프링이 빈 주입 시 사용
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthencationFilter authencationFilter, AuthEntryPoint exceptionHandler) {
        this.userDetailsService = userDetailsService;
        this.authencationFilter = authencationFilter;
        this.exceptionHandler = exceptionHandler;
    }

    // 글로벌 인증 매니저 설정 메서드 (비밀번호 인코더와 UserDetailsService 연결)
    public void configGlobal (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        // userDetailsService를 사용해 사용자 정보 조회, 비밀번호는 BCrypt 방식으로 인코딩해서 검증
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // 비밀번호 암호화를 위한 BCryptPasswordEncoder 빈 등록
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
        // AuthenticationManager 빈 등록 (인증을 처리하는 핵심 객체)
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // CSRF 보호 비활성화 (API 서버일 때 주로 비활성화함)

                .cors(Customizer.withDefaults())
                // CORS 기본 설정 활성화 (아래 corsConfigurationSource 빈과 연동)

                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 세션 관리 정책을 STATELESS로 설정 (서버에서 세션 저장하지 않음, JWT 등 토큰 방식에 적합)

                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                // POST /login 요청은 인증 없이 접근 허용
                                .anyRequest().authenticated()
                        // 나머지 모든 요청은 인증 필요
                )

                // 커스텀 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(authencationFilter, UsernamePasswordAuthenticationFilter.class)

                // 인증 실패 시 동작할 핸들러 설정
                .exceptionHandling(ex -> ex.authenticationEntryPoint(exceptionHandler));

        return http.build();
        // SecurityFilterChain 빈으로 반환
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("*"));
        // 모든 출처(origin)에 대해 CORS 허용

        config.setAllowedMethods(Arrays.asList("*"));
        // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)

        config.setAllowedHeaders(Arrays.asList("*"));
        // 모든 헤더 허용

        config.setAllowCredentials(false);
        // 자격 증명(쿠키, 인증정보 등)은 허용하지 않음

        config.applyPermitDefaultValues();
        // 기본값을 적용 (예: 특정 헤더 허용 등)

        source.registerCorsConfiguration("/**", config);
        // 모든 경로에 대해 위 CORS 설정을 적용

        return source;
        // CorsConfigurationSource 빈으로 반환
    }
}
