package com.onebyte.life4cut.user.exception;

import com.onebyte.life4cut.common.exception.CustomException;
import com.onebyte.life4cut.common.exception.ErrorCode;

public class RefreshTokenNotValid extends CustomException {

  public RefreshTokenNotValid() {
    super(ErrorCode.REFRESH_TOKEN_NOT_VALID);
  }
}
