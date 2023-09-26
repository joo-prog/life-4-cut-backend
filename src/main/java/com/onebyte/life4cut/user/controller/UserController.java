package com.onebyte.life4cut.user.controller;

import com.onebyte.life4cut.auth.dto.CustomUserDetails;
import com.onebyte.life4cut.common.web.ApiResponse;
import com.onebyte.life4cut.user.controller.dto.UserDuplicateResponse;
import com.onebyte.life4cut.user.controller.dto.UserFindResponse;
import com.onebyte.life4cut.user.domain.User;
import com.onebyte.life4cut.user.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;

  @GetMapping
  public ApiResponse<UserFindResponse> findUser(@RequestParam("nickname") String nickname) {
    User result = userService.findUserByNickname(nickname);
    return ApiResponse.OK(
        UserFindResponse.of(result)
    );
  }

  @GetMapping("/me")
  public ApiResponse<UserFindResponse> findMe(@AuthenticationPrincipal CustomUserDetails user) {
    User result = userService.findUser(user.getUserId());
    return ApiResponse.OK(
        UserFindResponse.of(result)
    );
  }

  @GetMapping("/duplicate")
  public ApiResponse<UserDuplicateResponse> checkDuplicatedNickname(@RequestParam("nickname") String nickname) {
    Optional<User> result = userService.findUserByNicknameForCheckingDuplication(nickname);
    return ApiResponse.OK(
        UserDuplicateResponse.of(result)
    );
  }
}
