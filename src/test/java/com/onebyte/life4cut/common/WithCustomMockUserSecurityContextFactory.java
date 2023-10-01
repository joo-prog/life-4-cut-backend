package com.onebyte.life4cut.common;

import com.onebyte.life4cut.auth.dto.CustomUserDetails;
import com.onebyte.life4cut.common.annotation.WithCustomMockUser;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithCustomMockUserSecurityContextFactory
    implements WithSecurityContextFactory<WithCustomMockUser> {

  @Override
  public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
    String username = annotation.username();
    String password = annotation.password();
    long userId = annotation.userId();

    // 여기서 바인딩되어 반환할 객체를 정의해주면 됩니다
    List<GrantedAuthority> roles = AuthorityUtils.createAuthorityList("ADMIN");
    CustomUserDetails user = new CustomUserDetails(username, password, userId, roles);

    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(user, "password", roles);
    SecurityContext context = SecurityContextHolder.getContext();
    context.setAuthentication(token);
    return context;
  }
}
