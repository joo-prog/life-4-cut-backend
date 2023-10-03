package com.onebyte.life4cut.auth.handler.jwt;

import com.onebyte.life4cut.auth.dto.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {
  private final RedisTemplate<String, String> redisTemplate;
  private static final String AUTHORITY_KEY = "auth";
  private static final String USER_ID = "userId";
  private final String secret;
  private final long expires;
  private final long refreshExpires;
  private final long cookieExpires;
  private Key key;

  public TokenProvider(
      RedisTemplate<String, String> redisTemplate,
      @Value("${auth.jwt.secret}") String secret,
      @Value("${auth.jwt.expires}") Duration expires,
      @Value("${auth.jwt.refresh-expires}") Duration refreshExpires) {
    this.redisTemplate = redisTemplate;
    this.secret = secret;
    this.expires = expires.toMillis();
    this.refreshExpires = refreshExpires.toMillis();
    this.cookieExpires = refreshExpires.toSeconds();
  }

  @Override
  public void afterPropertiesSet() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public String createAccessToken(Authentication authentication, long userId) {

    String authorities =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

    long now = System.currentTimeMillis(); // Date getTime 메소드보다 우수

    Map<String, Object> payload = new HashMap<>();
    payload.put(USER_ID, userId);
    payload.put(AUTHORITY_KEY, authorities);

    return Jwts.builder()
        .setSubject(authentication.getName())
        .setClaims(payload)
        .signWith(key, SignatureAlgorithm.HS512)
        .setExpiration(new Date(now + this.expires))
        .compact();
  }

  public String createRefreshToken(Authentication authentication, long userId) {
    String authorities =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
    long now = System.currentTimeMillis();

    Map<String, Object> payload = new HashMap<>();
    payload.put(USER_ID, userId);
    payload.put(AUTHORITY_KEY, authorities);

    String refreshToken =
        Jwts.builder()
            .setSubject(authentication.getName())
            .setClaims(payload)
            .setExpiration(new Date(now + this.refreshExpires))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

    redisTemplate
        .opsForValue()
        .set(authentication.getName(), refreshToken, refreshExpires, TimeUnit.MICROSECONDS);
    return refreshToken;
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

    log.debug("Token Expiration: {}", claims.getExpiration());

    List<GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITY_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    long userId = claims.get(USER_ID, Long.class);

    CustomUserDetails principal =
        new CustomUserDetails(claims.getSubject(), "", userId, authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

      log.info("잘못된 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {

      log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {

      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalArgumentException e) {

      log.info("JWT 토큰이 잘못되었습니다.");
    }
    return false;
  }

  public ResponseCookie makeTokenCookie(String name, String token) {
    return ResponseCookie.from(name, token)
        .httpOnly(true)
        .secure(true)
        .maxAge(cookieExpires)
        .path("/")
        .build();
  }
}
