package com.onebyte.life4cut.album.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CreatePictureRequest(
        @Min(value = 1, message = "아이디는 1보다 커야합니다")
        Long slotId,
        @NotBlank(message = "내용을 입력해주세요")
        String content,
        @NotNull(message = "태그를 입력해주세요")
        List<@NotBlank String> tags,
        @NotNull(message = "사진을 찍은 날짜를 입력해주세요")
        LocalDate picturedAt) {
}
