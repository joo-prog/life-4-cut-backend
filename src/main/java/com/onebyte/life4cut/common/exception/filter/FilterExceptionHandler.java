package com.onebyte.life4cut.common.exception.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebyte.life4cut.common.exception.CustomErrorResponse;
import com.onebyte.life4cut.common.exception.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilterExceptionHandler {

  private final ObjectMapper objectMapper;

  public void sendErrorResponse(HttpServletResponse response, CustomException customException) {
    response.setStatus(customException.getStatus().value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    CustomErrorResponse errorResponse = new CustomErrorResponse(customException.getMessage());
    try {
      response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
