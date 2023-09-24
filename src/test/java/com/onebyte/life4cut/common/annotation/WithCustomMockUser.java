package com.onebyte.life4cut.common.annotation;

import com.onebyte.life4cut.common.WithCustomMockUserSecurityContextFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {

  String username() default "bell";
  String password() default "1234";
  long userId() default 1L;

}
