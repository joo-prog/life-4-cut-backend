package com.onebyte.life4cut.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionName {
  BAD_REQUEST("Bad Request"),
  SAMPLE_NOT_FOUND("Sample Not Found"),

  RUNTIME_ERROR("Runtime Error"),
  INTERNAL_SERVER_ERROR("Internal Server Error"),
  FORBIDDEN("Forbidden")
  ;

  private final String name;
}
