package com.onebyte.life4cut.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

  BAD_REQUEST("Bad Request"),
  SAMPLE_NOT_FOUND("Sample Not Found"),
  USER_NOT_FOUND("User Not Found"),

  INTERNAL_SERVER_ERROR("Internal Server Error"),
  FORBIDDEN("Forbidden")
  ;

  private final String message;
}
