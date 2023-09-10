package com.onebyte.life4cut.auth.dto;

import com.onebyte.life4cut.common.constants.OAuthType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthInfo {

  private String email;
  private OAuthType oauthType;
  private String oauthId;
  private String secret;

  public void setOAuthInfo(String email, OAuthType oauthType, String oauthId, String secret) {
    this.email = email;
    this.oauthType = oauthType;
    this.oauthId = oauthId;
    this.secret = secret;
  }
}
