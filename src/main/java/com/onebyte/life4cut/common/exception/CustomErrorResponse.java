package com.onebyte.life4cut.common.exception;

import jakarta.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class CustomErrorResponse {

  public CustomErrorResponse(String message) {
    this.message = message;
  }

  @NotBlank private final String message;

  private final Map<String, String> data = new HashMap<>();
}
