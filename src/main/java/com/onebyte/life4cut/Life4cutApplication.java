package com.onebyte.life4cut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Life4cutApplication {
  public static void main(String[] args) {
    SpringApplication.run(Life4cutApplication.class, args);
  }
}
