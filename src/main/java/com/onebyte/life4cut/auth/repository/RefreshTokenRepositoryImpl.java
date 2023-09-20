package com.onebyte.life4cut.auth.repository;

import com.onebyte.life4cut.auth.domain.RefreshToken;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

  private final RedisTemplate redisTemplate;

  public void save(RefreshToken refreshToken) {
    ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
    valueOperations.set(refreshToken.getRefreshToken(), refreshToken.getUserId());
    redisTemplate.expire(refreshToken.getRefreshToken(), 60 * 60 * 24, TimeUnit.SECONDS);
  }

  public Optional<RefreshToken> findById(String refreshToken) {
    ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
    Long userId = valueOperations.get(refreshToken);
    if (Objects.isNull(userId)) {
      return Optional.empty();
    }
    return Optional.of(new RefreshToken(refreshToken, userId));
  }
}
