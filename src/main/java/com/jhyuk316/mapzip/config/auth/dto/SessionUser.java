package com.jhyuk316.mapzip.config.auth.dto;

import com.jhyuk316.mapzip.model.MemberEntity;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;

    public SessionUser(MemberEntity memberEntity) {
        this.name = memberEntity.getName();
        this.email = memberEntity.getEmail();
    }

}
