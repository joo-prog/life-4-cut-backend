package com.onebyte.life4cut.album.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CreatePictureRequest(Long slotId, String content, List<String> tags, LocalDateTime picturedAt) {
}
