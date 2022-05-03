package com.jhyuk316.mapzip.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import com.jhyuk316.mapzip.dto.MemberDTO;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"}) // ok
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void saveMember() {
        // given
        MemberDTO dto = MemberDTO.builder()
            .userId("ADMIN")
            .password("1234")
            .email("admin@mapzip.com")
            .build();

        // when
        MemberDTO saved = memberService.saveMember(dto);

        // then
        System.out.println(saved.getUserId());
        System.out.println(saved.getPassword());
        assertThat(saved.getUserId()).isEqualTo(dto.getUserId());
        // assertThat(saved.getPassword()).isEqualTo(passwordEncoder.encode(dto.getPassword()));
        // 이방식으로는 제대로 인코딩 되었는 지 알 수 없음.
        assertThat(passwordEncoder.matches(dto.getPassword(), saved.getPassword())).isEqualTo(true);
        assertThat(saved.getEmail()).isEqualTo(dto.getEmail());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    void validateDuplicateMember() {
        // given
        MemberDTO dto = MemberDTO.builder()
            .userId("ADMIN")
            .password("1234")
            .email("admin@mapzip.com")
            .build();
        MemberDTO saved = memberService.saveMember(dto);

        // when
        Throwable thrown = catchThrowable(() -> memberService.saveMember(dto));

        // then
        then(thrown)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("이미 가입된 회원입니다.");
    }
}