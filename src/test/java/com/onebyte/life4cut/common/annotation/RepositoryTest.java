package com.onebyte.life4cut.common.annotation;

import com.onebyte.life4cut.config.JpaConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest
@Import({JpaConfiguration.class})
@ComponentScan(basePackages = "com.onebyte.life4cut.fixture")
public @interface RepositoryTest {

  @AliasFor(annotation = Import.class, attribute = "value")
  Class<?>[] value() default {};
}
