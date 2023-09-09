package com.onebyte.life4cut.user.repository;

import com.onebyte.life4cut.user.domain.User;
import java.util.Optional;

public interface UserRepository {

  Optional<User> findUser(int id);
}
