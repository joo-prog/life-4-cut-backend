package com.onebyte.life4cut.picture.service;

import com.onebyte.life4cut.album.repository.AlbumQueryRepository;
import com.onebyte.life4cut.album.repository.SlotQueryRepository;
import com.onebyte.life4cut.album.repository.UserAlbumQueryRepository;
import com.onebyte.life4cut.common.constants.S3Env;
import com.onebyte.life4cut.picture.repository.PictureRepository;
import com.onebyte.life4cut.support.fileUpload.FileUploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;


class PictureServiceTest {

    private final AlbumQueryRepository albumQueryRepository = mock(AlbumQueryRepository.class);
    private final SlotQueryRepository slotQueryRepository = mock(SlotQueryRepository.class);
    private final UserAlbumQueryRepository userAlbumQueryRepository = mock(UserAlbumQueryRepository.class);
    private final PictureRepository pictureRepository = mock(PictureRepository.class);
    private final FileUploader fileUploader = mock(FileUploader.class);
    private final S3Env s3Env = new S3Env("test");
    private final PictureService pictureService = new PictureService(
            slotQueryRepository,
            albumQueryRepository,
            userAlbumQueryRepository,
            pictureRepository,
            fileUploader,
            s3Env
    );


    @Nested
    class CreateInSlot {

        @Test
        @DisplayName("사진을 추가한다")
        void createInSlot() {
            // given
            Long authorId = 1L;
            Long albumId = 1L;
            Long slotId = 1L;
            String content = "content";
            LocalDateTime picturedAt = LocalDateTime.of(2023, 9, 23, 1, 1, 1);
            List<String> tags = List.of("tag1", "tag2");
            MultipartFile image = new MockMultipartFile("image.png", "original-name.png", MimeTypeUtils.IMAGE_PNG_VALUE, "test".getBytes());

            // when
            Long pictureId = pictureService.createInSlot(authorId, albumId, slotId, content, picturedAt, tags, image);

            // then
        }

    }
}