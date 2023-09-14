package com.onebyte.life4cut.auth.filter;

import com.onebyte.life4cut.auth.domain.RefreshToken;
import com.onebyte.life4cut.auth.dto.CustomUserDetails;
import com.onebyte.life4cut.auth.handler.jwt.TokenProvider;
import com.onebyte.life4cut.auth.repository.RefreshTokenRepository;
import com.onebyte.life4cut.common.exception.filter.FilterExceptionHandler;
import com.onebyte.life4cut.user.exception.RefreshTokenNotValid;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final FilterExceptionHandler filterExceptionHandler;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String jwt = resolveToken(httpServletRequest);
    String requestUri = httpServletRequest.getRequestURI();

    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
      Authentication authentication = tokenProvider.getAuthentication(jwt);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestUri);
    } else {
      checkRefreshToken(request, response);
      log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestUri);
    }

    filterChain.doFilter(request, response);
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  private String resolveRefreshToken(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie: cookies) {
      String name = cookie.getName();
      if (name.equals("refreshToken")) {
        return cookie.getValue();
      }
    }
    return null;
  }

  private void checkRefreshToken(HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = resolveRefreshToken(request);
    log.info("Check Refresh Token: {}", refreshToken);
    if (refreshToken == null || refreshToken.equals("")) {
      return;
    }

    Authentication authentication = tokenProvider.getAuthentication(refreshToken);
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    validateRefreshToken(refreshToken, userDetails, response);

    String jwt = tokenProvider.createToken(authentication, userDetails.getUserId(), userDetails.getNickname());
    log.info("new JWT TOKEN: {}", jwt);
    response.setHeader("Authorization", "Bearer " + jwt);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private void validateRefreshToken(String refreshToken, CustomUserDetails userDetails, HttpServletResponse response) {
    log.info("Validate Refresh Token");
    Optional<RefreshToken> findRefresh = refreshTokenRepository.findById(refreshToken);
    if (findRefresh.isEmpty()) {
      filterExceptionHandler.sendErrorResponse(response, new RefreshTokenNotValid());
    }
    long userId = findRefresh.get().getUserId();
    long tokenUserId = userDetails.getUserId();

    if (userId != tokenUserId) {
      filterExceptionHandler.sendErrorResponse(response, new RefreshTokenNotValid());
    }
  }
}
