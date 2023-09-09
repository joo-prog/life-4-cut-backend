package com.onebyte.life4cut.user.service;

import com.onebyte.life4cut.user.domain.User;
import com.onebyte.life4cut.user.exception.UserNotFound;
import com.onebyte.life4cut.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public User findUser(int id) {
    User user = userRepository.findUser(id)
        .orElseThrow(UserNotFound::new);
    return user;
  }
}
