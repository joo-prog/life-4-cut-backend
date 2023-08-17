package com.onebyte.life4cut.common.exception;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomErrorResponse {
  @NotBlank
  private String name;

  @NotBlank
  private String message;
}
