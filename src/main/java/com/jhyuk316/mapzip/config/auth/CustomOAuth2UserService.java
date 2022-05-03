package com.jhyuk316.mapzip.config.auth;

import com.jhyuk316.mapzip.config.auth.dto.OAuthAttributesDTO;
import com.jhyuk316.mapzip.config.auth.dto.SessionUser;
import com.jhyuk316.mapzip.model.MemberEntity;
import com.jhyuk316.mapzip.persistence.MemberRepository;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
            .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributesDTO attributesDTO = OAuthAttributesDTO.of(registrationId,
            userNameAttributeName, oAuth2User.getAttributes());

        MemberEntity memberEntity = saveOrUpdate(attributesDTO);

        httpSession.setAttribute("user", new SessionUser(memberEntity));

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(memberEntity.getRole().name())),
            attributesDTO.getAttributes(), attributesDTO.getNameAttributeKey());
    }

    private MemberEntity saveOrUpdate(OAuthAttributesDTO attributesDTO) {
        MemberEntity member = memberRepository.findByEmail(attributesDTO.getEmail())
            .map(entity -> entity.update(attributesDTO.getName()))
            .orElse(attributesDTO.toEntity());
        return memberRepository.save(member);
    }
}
