package com.onebyte.life4cut.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  BAD_REQUEST(HttpStatus.BAD_REQUEST, ExceptionMessage.BAD_REQUEST.getMessage()),
  NOT_SUPPORT_OAUTH_TYPE(HttpStatus.BAD_REQUEST, ExceptionMessage.NOT_SUPPORT_OAUTH_TYPE.getMessage()),
  SAMPLE_NOT_FOUND(HttpStatus.NOT_FOUND, ExceptionMessage.SAMPLE_NOT_FOUND.getMessage()),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, ExceptionMessage.USER_NOT_FOUND.getMessage()),
  ;

  private final HttpStatus status;
  private final String message;
}
