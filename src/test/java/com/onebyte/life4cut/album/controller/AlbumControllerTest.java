package com.onebyte.life4cut.album.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.FieldDescriptors;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.onebyte.life4cut.album.controller.dto.CreatePictureRequest;
import com.onebyte.life4cut.common.annotation.WithCustomMockUser;
import com.onebyte.life4cut.common.controller.ControllerTest;
import com.onebyte.life4cut.picture.service.PictureService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.ResultActions;


@WebMvcTest(AlbumController.class)
class AlbumControllerTest extends ControllerTest {

    @MockBean
    private PictureService pictureService;

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

            MockMultipartFile image = new MockMultipartFile("image", "original-name.png",
                MediaType.IMAGE_PNG_VALUE, "image".getBytes());
            MockMultipartFile data = new MockMultipartFile("data", "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request).getBytes());

            when(pictureService.createInSlot(any(), any(), any(), any(), any(), any(),
                any())).thenReturn(100L);

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
                        resource(
                            ResourceSnippetParameters.builder()
                                .description("사진을 업로드한다")
                                .summary("사진을 업로드한다")
                                .pathParameters(
                                    parameterWithName("albumId").description("앨범 아이디")
                                        .type(SimpleType.NUMBER)
                                )
                                .responseFields(
                                    fieldWithPath("message").type(STRING).description("응답 메시지"),
                                    fieldWithPath("data.id").type(NUMBER).description("사진 아이디")
                                )
                                .build()
                        ),
                        requestPartFields("data",
                            fieldWithPath("slotId").type(NUMBER).description("슬롯 아이디"),
                            fieldWithPath("content").type(JsonFieldType.STRING)
                                .description("사진 내용"),
                            fieldWithPath("tags[]").type(JsonFieldType.ARRAY)
                                .description("사진 태그 목록")
                                .attributes(Attributes.key("itemType").value(JsonFieldType.STRING)),
                            fieldWithPath("picturedAt").description("사진 찍은 날짜")
                        ),
                        requestPartBody("image")
                    )
                );
        }
    }
}