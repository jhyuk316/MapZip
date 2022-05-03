package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.MemberDTO;
import com.jhyuk316.mapzip.model.MemberEntity;
import com.jhyuk316.mapzip.persistence.MemberRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberDTO saveMember(MemberDTO memberDTO) {
        MemberEntity entity = MemberEntity.createMember(memberDTO, passwordEncoder);
        validateDuplicateMember(entity);
        MemberEntity result = memberRepository.save(entity);
        return new MemberDTO(result);
    }

    private void validateDuplicateMember(MemberEntity memberEntity) {
        MemberEntity findMember = memberRepository.findByEmail(memberEntity.getEmail())
            .orElse(null);
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}
