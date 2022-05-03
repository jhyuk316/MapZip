package com.jhyuk316.mapzip.config.auth.dto;

import com.jhyuk316.mapzip.constant.Role;
import com.jhyuk316.mapzip.model.MemberEntity;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributesDTO {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthAttributesDTO(
        Map<String, Object> attributes,
        String nameAttributeKey,
        String name,
        String email,
        String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributesDTO of(String registrationId, String userNameAttributeName,
        Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributesDTO ofGoogle(String userNameAttributeName,
        Map<String, Object> attributes) {
        return OAuthAttributesDTO.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    public MemberEntity toEntity() {
        return MemberEntity.builder()
            .name(name)
            .email(email)
            .role(Role.USER)
            .build();
    }

}
