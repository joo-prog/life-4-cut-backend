package com.onebyte.life4cut.user.controller.dto;

import com.onebyte.life4cut.user.domain.User;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDuplicateResponse {

  // boolean으로 두면 isDuplicated가 duplicated로 자동으로 변경되어 버림..?
  private final Boolean isDuplicated;


  public static UserDuplicateResponse of(Optional<User> optionalUser) {
    if (optionalUser.isPresent()) {
      return new UserDuplicateResponse(true);
    }

    return new UserDuplicateResponse(false);
  }
}
