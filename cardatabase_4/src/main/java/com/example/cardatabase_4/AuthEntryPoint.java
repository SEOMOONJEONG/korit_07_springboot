package com.example.cardatabase_4;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component  // 이 클래스가 스프링의 컴포넌트임을 나타냅니다. 스프링 컨테이너가 관리하는 빈(bean)으로 등록됩니다.
public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 인증이 필요한 자원에 인증 없이 접근할 때 호출되는 메서드입니다.
        // HttpServletRequest: 요청 정보가 담긴 객체
        // HttpServletResponse: 응답 정보를 담아 보낼 객체
        // AuthenticationException: 인증 실패 관련 예외 정보가 담긴 객체
        // IOException, ServletException: 입출력 또는 서블릿 관련 예외 처리 선언
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // HTTP 응답 상태 코드를 401 (Unauthorized)로 설정합니다.
        // 즉, 인증이 필요하다는 의미를 클라이언트에게 전달합니다.
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 응답의 Content-Type 헤더를 application/json 으로 설정합니다.
        // 클라이언트에게 JSON 형식의 데이터를 보낼 것임을 알립니다.
        PrintWriter writer = response.getWriter();
        // 응답 바디에 데이터를 쓰기 위한 PrintWriter 객체를 얻습니다.
        writer.println("Error : " + authException.getMessage());
        // 응답 바디에 인증 실패 원인을 문자열로 출력합니다.
        // 예: "Error : Full authentication is required to access this resource"
    }
}