package com.onebyte.life4cut.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{

  private final HttpStatus status;

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    status = errorCode.getStatus();
  }

  public CustomException(ErrorCode errorCode, Throwable throwable) {
    super(errorCode.getMessage(), throwable);
    status = errorCode.getStatus();
  }
}
