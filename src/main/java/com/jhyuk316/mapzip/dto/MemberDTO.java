package com.jhyuk316.mapzip.dto;

import com.jhyuk316.mapzip.model.MemberEntity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberDTO {

    @NotEmpty(message = "ID는 필수입니다.")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자이상 16자 이하로 입력해주세요.")
    private String password;

    private String name;

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    private String email;


    public MemberDTO(final MemberEntity entity) {
        userId = entity.getUserId();
        password = entity.getPassword();
        name = entity.getName();
        email = entity.getEmail();
    }

    //    public MemberEntity toEntity() {
    //        return MemberEntity.builder()
    //            .userId(userId)
    //            .password(password)
    //            .email(email)
    //            .build();
    //    }
}
