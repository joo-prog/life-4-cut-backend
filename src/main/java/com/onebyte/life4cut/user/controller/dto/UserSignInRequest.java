package com.onebyte.life4cut.user.controller.dto;

import com.onebyte.life4cut.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserSignInRequest {

  private final String email;
  private final String nickname;
  private final String oauthType;
  private final String oauthId;

  public User toEntity() {
    return User.builder()
        .nickname(nickname)
        .email(email)
        .oauthType(oauthType)
        .oauthId(oauthId)
        .build();
  }

}
