package com.example.cardatabase_4.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
// 스프링의 서비스 컴포넌트로 등록됨. JWT 관련 기능 담당
public class JwtService {

    static final long EXPIRATION = 86400000;
    // 토큰 만료 시간: 86400000 밀리초 = 24시간

    static final String PREFIX = "Bearer";
    // HTTP Authorization 헤더에서 토큰 앞에 붙는 접두어 ("Bearer ")

    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // JWT 서명에 사용할 비밀키 생성 (HS256 알고리즘 사용)
    // 보통 애플리케이션 시작 시 고정된 키를 사용하지만 여기선 매번 새로 생성됨

    public String getToken(String username) {
        // username을 기반으로 JWT 토큰 생성

        String token = Jwts.builder()
                .setSubject(username)
                // 토큰의 subject(주체)를 username으로 설정 (누구에 대한 토큰인지)

                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                // 토큰 만료시간 설정: 현재 시간 + EXPIRATION

                .signWith(key)
                // 위에서 생성한 비밀키로 토큰 서명

                .compact();
        // 최종 JWT 문자열로 압축(생성)

        return token;
        // 생성된 JWT 토큰 반환
    }

    public String getAuthUser(HttpServletRequest request) {
        // HTTP 요청 헤더에서 JWT 토큰을 추출하고, 토큰에서 username(주체)을 반환

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        // HTTP Authorization 헤더에서 토큰 문자열 가져오기

        if(token != null) {
            // 토큰이 null이 아니면

            String user = Jwts.parser()
                    .setSigningKey(key)
                    // 토큰 검증에 사용할 비밀키 설정

                    .build()
                    // JWT 파서 객체 생성

                    .parseClaimsJws(token.replace(PREFIX, "").trim())
                    // "Bearer " 접두어 제거하고 토큰 파싱 및 검증
                    // 검증에 실패하면 예외 발생

                    .getBody()
                    // JWT 페이로드(Claims) 가져오기

                    .getSubject();
            // Claims에서 subject(즉 username) 추출

            if (user != null) return user;
            // username이 null이 아니면 반환
        }

        return null;
        // 토큰이 없거나 유효하지 않으면 null 반환
    }
}

