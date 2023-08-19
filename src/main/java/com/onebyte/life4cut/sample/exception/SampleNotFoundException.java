package com.onebyte.life4cut.sample.exception;

import com.onebyte.life4cut.common.exception.CustomException;
import com.onebyte.life4cut.common.exception.ErrorCode;

public class SampleNotFoundException extends CustomException {

  public SampleNotFoundException() {
    super(ErrorCode.SAMPLE_NOT_FOUND);
  }
}
