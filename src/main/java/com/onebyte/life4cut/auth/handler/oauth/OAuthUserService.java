package com.onebyte.life4cut.auth.handler.oauth;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OAuthUserService extends DefaultOAuth2UserService {
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    OAuth2User oAuth2User = super.loadUser(userRequest);

    switch (registrationId) {
      case "kakao-login" -> log.info("kakao login");
      case "naver-login" -> log.info("naver login");
      case "google-login" -> log.info("google login");
    }

    List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ADMIN");

    String userNameAttributeName =
        userRequest
            .getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();

    return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName);
  }
}
