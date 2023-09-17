package com.onebyte.life4cut.user.service;

import com.onebyte.life4cut.auth.dto.OAuthInfo;
import com.onebyte.life4cut.user.domain.User;
import com.onebyte.life4cut.user.controller.dto.UserSignInRequest;
import com.onebyte.life4cut.user.exception.UserNotFound;
import com.onebyte.life4cut.user.exception.UserNotUnique;
import com.onebyte.life4cut.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public User save(UserSignInRequest request) {
    return userRepository.save(request.toEntity());
  }

  public User findUser(long id) {
    User user = userRepository.findUser(id)
        .orElseThrow(UserNotFound::new);
    return user;
  }

  public Optional<User> findUserByOAuthInfo(OAuthInfo oAuthInfo) {
    List<User> result = userRepository.findUserByOAuthInfo(oAuthInfo);
    if (result.size() > 1) {
      throw new UserNotUnique();
    }
    return result.stream().findAny();
  }

  public User findUserByNickname(String nickname) {
    return userRepository.findUserByNickname(nickname)
        .orElseThrow(UserNotFound::new);
  }
}
