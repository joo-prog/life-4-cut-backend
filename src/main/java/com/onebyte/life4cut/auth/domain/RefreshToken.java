package com.onebyte.life4cut.auth.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 24 * 60 * 60)
public class RefreshToken {

  @Id
  private final String refreshToken;
  private final long userId;

  public RefreshToken(String refreshToken, long userId) {
    this.refreshToken = refreshToken;
    this.userId = userId;
  }

}
