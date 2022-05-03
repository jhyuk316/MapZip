package com.jhyuk316.mapzip.model;

import com.jhyuk316.mapzip.constant.Role;
import com.jhyuk316.mapzip.dto.MemberDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@Data
@Entity
public class MemberEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String userId;
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public static MemberEntity createMember(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
        String passwordEncoded = passwordEncoder.encode(memberDTO.getPassword());
        return MemberEntity.builder()
            .userId(memberDTO.getUserId())
            .password(passwordEncoded)
            .email(memberDTO.getEmail())
            .role(Role.USER)
            .build();
    }

    public MemberEntity update(String name) {
        this.name = name;
        return this;
    }
}
