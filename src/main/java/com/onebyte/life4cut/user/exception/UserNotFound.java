package com.onebyte.life4cut.user.exception;

import com.onebyte.life4cut.common.exception.CustomException;
import com.onebyte.life4cut.common.exception.ErrorCode;

public class UserNotFound extends CustomException {
  public UserNotFound() {
    super(ErrorCode.USER_NOT_FOUND);
  }
}
