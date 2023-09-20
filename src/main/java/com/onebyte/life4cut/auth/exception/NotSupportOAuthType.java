package com.onebyte.life4cut.auth.exception;

import com.onebyte.life4cut.common.exception.CustomException;
import com.onebyte.life4cut.common.exception.ErrorCode;

public class NotSupportOAuthType extends CustomException {

  public NotSupportOAuthType() {
    super(ErrorCode.NOT_SUPPORT_OAUTH_TYPE);
  }
}
