package com.example.cardatabase_4.service;

import com.example.cardatabase_4.domain.AppUser;
import com.example.cardatabase_4.domain.AppUserRepository;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
// 이 클래스는 스프링의 서비스 컴포넌트임을 나타냄. Spring Bean으로 자동 등록됩니다.
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    // AppUser 데이터를 DB에서 조회하기 위한 리포지토리 의존성 선언

    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
        // 생성자를 통해 AppUserRepository 주입 (DI)
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 스프링 시큐리티가 인증할 때 호출하는 메서드
        // 입력받은 username을 기반으로 사용자 정보를 조회하고 UserDetails 객체를 반환해야 함

        Optional<AppUser> user = appUserRepository.findByUsername(username);
        // DB에서 username으로 AppUser를 조회 (Optional로 감쌈)

        UserBuilder builder = null;
        // UserDetails.UserBuilder를 담을 변수 선언 (나중에 사용자 정보를 세팅할 빌더)
        // User.UserBuilder import문 확인필요

        if(user.isPresent()) {
            // 조회된 사용자가 있으면

            AppUser currentUser = user.get();
            // Optional에서 실제 AppUser 객체를 꺼냄

            builder = withUsername(username);
            // 스프링 시큐리티 내장 User.UserBuilder의 withUsername 정적 메서드 호출로 빌더 생성
            // username을 빌더에 설정

            builder.password(currentUser.getPassword()).roles(currentUser.getRole());
            // 빌더에 패스워드와 권한(role) 정보를 설정
            // roles()는 가변 인자(String...)를 받으며 내부적으로 권한 리스트로 변환

        } else {
            // 조회된 사용자가 없으면

            throw new UsernameNotFoundException("User Not Found");
            // 사용자 없음을 알리는 예외를 던져 스프링 시큐리티가 인증 실패 처리하게 함
        }

        return builder.build();
        // 완성된 UserDetails 객체를 반환 (스프링 시큐리티가 내부적으로 인증 절차 진행)
    }
}

