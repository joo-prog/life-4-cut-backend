package com.onebyte.life4cut.user.exception;

import com.onebyte.life4cut.common.exception.CustomException;
import com.onebyte.life4cut.common.exception.ErrorCode;

public class UserNotUnique extends CustomException {

  public UserNotUnique() {
    super(ErrorCode.USER_NOT_UNIQUE);
  }
}
