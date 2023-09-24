package com.onebyte.life4cut.album.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebyte.life4cut.album.controller.dto.CreatePictureRequest;
import com.onebyte.life4cut.annotation.ControllerTest;
import com.onebyte.life4cut.annotation.WithCustomMockUser;
import com.onebyte.life4cut.picture.service.PictureService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(AlbumController.class)
class AlbumControllerTest {
    @MockBean
    private PictureService pictureService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @WithCustomMockUser
    class UploadPicture {
        @Test
        @DisplayName("사진을 업로드한다")
        void uploadPicture() throws Exception {
            // given
            Long slotId = 1L;
            String content = "content";
            List<String> tags = List.of("tag1", "tag2");
            LocalDate picturedAt = LocalDate.of(2021, 1, 1);
            CreatePictureRequest request = new CreatePictureRequest(
                    slotId,
                    content,
                    tags,
                    picturedAt
            );

            MockMultipartFile image = new MockMultipartFile("image", "original-name.png", MediaType.IMAGE_PNG_VALUE, "image".getBytes());
            MockMultipartFile data = new MockMultipartFile("data", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsString(request).getBytes());

            when(pictureService.createInSlot(any(), any(), any(), any(), any(), any(), any())).thenReturn(100L);

            // when
            ResultActions result = mockMvc.perform(
                    multipart("/api/v1/albums/{albumId}/pictures", 1L)
                            .file(image)
                            .file(data)
            );

            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("data.id", equalTo(100)))
                    .andDo(
                            document(
                                    "{class_name}/{method_name}",
                                    requestPartFields("data",
                                            fieldWithPath("slotId").type(NUMBER).description("슬롯 아이디"),
                                            fieldWithPath("content").type(JsonFieldType.STRING).description("사진 내용"),
                                            fieldWithPath("tags[]").type(JsonFieldType.ARRAY).description("사진 태그 목록")
                                                    .attributes(Attributes.key("itemType").value(JsonFieldType.STRING)),
                                            fieldWithPath("picturedAt").description("사진 찍은 날짜")
                                    ),
                                    requestPartBody("image"),
                                    responseFields(
                                            fieldWithPath("message").type(STRING).description("응답 메시지"),
                                            fieldWithPath("data.id").type(NUMBER).description("사진 아이디")
                                    )
                            )
                    );
        }
    }
}