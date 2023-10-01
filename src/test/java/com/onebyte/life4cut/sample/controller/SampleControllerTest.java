package com.onebyte.life4cut.sample.controller;


import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebyte.life4cut.sample.controller.dto.SampleCreateRequest;
import com.onebyte.life4cut.sample.service.SampleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

class SampleControllerTest {
    @MockBean
    private SampleService sampleService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    class Create {
        @Test
        @DisplayName("유저를 생성하고 유저아이디를 반환한다")
        void createUserAndResponseUserId() throws Exception {
            // given
            SampleCreateRequest request = new SampleCreateRequest("nickname", "email@gmail.com");

            when(sampleService.save(any(), any())).thenReturn(100L);

            // when
            ResultActions result = mockMvc.perform(post("/api/v1/samples")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            result
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("data.id", equalTo(100)))
                    .andDo(
                            document(
                                    "{class_name}/{method_name}",
                                    requestFields(
                                            fieldWithPath("nickname").type(STRING).description("만들 샘플 닉네임"),
                                            fieldWithPath("email").type(STRING).description("만들 샘플 이메일")
                                    ),
                                    responseFields(
                                            fieldWithPath("message").type(STRING).description("응답 메시지"),
                                            fieldWithPath("data.id").type(NUMBER).description("만들어진 샘플 아이디")
                                    )
                            )
                    );
        }
    }
}