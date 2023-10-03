package com.onebyte.life4cut.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OAuthType {
  KAKAO_LOGIN("kakao"),
  NAVER_LOGIN("naver"),
  GOOGLE_LOGIN("google"),
  ;

  private final String type;
}
