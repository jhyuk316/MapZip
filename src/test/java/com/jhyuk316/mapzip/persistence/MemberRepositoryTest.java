package com.jhyuk316.mapzip.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhyuk316.mapzip.constant.Role;
import com.jhyuk316.mapzip.model.MemberEntity;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"}) // ok
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveMember() {
        // gaven
        String userId = "admin";
        String password = "1234";
        String email = "abc@abc.com";
        Role role = Role.ADMIN;

        // when
        MemberEntity saved = memberRepository.save(MemberEntity.builder()
            .userId(userId)
            .password(password)
            .email(email)
            .role(role)
            .build());
        System.out.println("result id : " + saved.getId());

        // then
        MemberEntity result = memberRepository.findById(saved.getId())
            .orElseThrow(() -> new NoSuchElementException());
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getPassword()).isEqualTo(password);
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getRole()).isEqualTo(role);

        result = memberRepository.findByEmail(saved.getEmail()).orElse(null);
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getPassword()).isEqualTo(password);
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getRole()).isEqualTo(role);
    }
}