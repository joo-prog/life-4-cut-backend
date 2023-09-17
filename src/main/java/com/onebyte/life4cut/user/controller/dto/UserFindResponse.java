package com.onebyte.life4cut.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserFindResponse {

  private final long userId;
  private final String nickname;
  private final String email;

}
