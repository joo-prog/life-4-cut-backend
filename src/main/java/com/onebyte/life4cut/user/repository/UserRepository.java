package com.onebyte.life4cut.user.repository;

import com.onebyte.life4cut.auth.dto.OAuthInfo;
import com.onebyte.life4cut.user.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

  User save(User user);

  Optional<User> findUser(long id);

  List<User> findUserByOAuthInfo(OAuthInfo oAuthInfo);

  Optional<User> findUserByNickname(String nickname);
}
