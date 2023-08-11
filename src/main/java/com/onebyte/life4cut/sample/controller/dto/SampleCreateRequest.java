package com.onebyte.life4cut.sample.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onebyte.life4cut.common.vo.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SampleCreateRequest(
        @NotBlank
        @Size(max = 20)
        String nickname,
        @NotBlank
        @JsonProperty("email")
        String email
) {

    public Email getEmail() {
        return Email.of(email);
    }
}