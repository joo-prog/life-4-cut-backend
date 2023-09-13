package com.onebyte.life4cut.auth.repository;

import com.onebyte.life4cut.auth.domain.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository {

  public void save(RefreshToken refreshToken);
  public Optional<RefreshToken> findById(String refreshToken);
}
