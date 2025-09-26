package com.example.cardatabase;

import com.example.cardatabase.domain.AppUser;
import com.example.cardatabase.domain.AppUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AppRepositoryTest {
    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    @DisplayName("findByUsername 메서드 검증")
    void findByUsernameShouldReturnAppUser() {
        // given
        appUserRepository.save(new AppUser("user1", "user1", "USER"));

        // when
        Optional<AppUser> foundUser = appUserRepository.findByUsername("user1");

        // then - 작성 , when 미작성
        assertThat(appUserRepository.findByUsername("user1").isPresent()).isTrue();

        // then - 정답
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getRole()).isEqualTo("USER");
    }
}
