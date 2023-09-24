package com.onebyte.life4cut.user.controller.dto;

import com.onebyte.life4cut.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFindResponse {

  private final long userId;
  private final String nickname;
  private final String email;
  private final String profilePath;


  public static UserFindResponse of(User user) {
    return new UserFindResponse(user.getId(), user.getNickname(), user.getEmail(),
        user.getProfilePath());
  }
}
