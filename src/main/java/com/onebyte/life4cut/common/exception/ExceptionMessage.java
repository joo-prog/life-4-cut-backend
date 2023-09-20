package com.onebyte.life4cut.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

  BAD_REQUEST("Bad Request"),
  NOT_SUPPORT_OAUTH_TYPE("지원하지 않는 OAuth Type 입니다."),
  USER_NOT_UNIQUE("OAuthInfo로 찾은 유저가 고유하지 않습니다."),
  REFRESH_TOKEN_NOT_VALID("Refresh Token이 유효하지 않습니다."),
  SAMPLE_NOT_FOUND("Sample Not Found"),
  USER_NOT_FOUND("User Not Found"),

  INTERNAL_SERVER_ERROR("Internal Server Error"),
  FORBIDDEN("Forbidden")
  ;

  private final String message;
}
