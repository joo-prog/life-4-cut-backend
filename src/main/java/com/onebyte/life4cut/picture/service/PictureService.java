package com.onebyte.life4cut.picture.service;

import com.onebyte.life4cut.album.domain.Album;
import com.onebyte.life4cut.album.domain.Slot;
import com.onebyte.life4cut.album.domain.UserAlbum;
import com.onebyte.life4cut.album.exception.AlbumDoesNotHaveSlotException;
import com.onebyte.life4cut.album.exception.AlbumNotFoundException;
import com.onebyte.life4cut.album.exception.SlotNotFoundException;
import com.onebyte.life4cut.album.exception.UserAlbumRolePermissionException;
import com.onebyte.life4cut.album.repository.AlbumQueryRepository;
import com.onebyte.life4cut.album.repository.SlotQueryRepository;
import com.onebyte.life4cut.album.repository.UserAlbumQueryRepository;
import com.onebyte.life4cut.album.repository.UserAlbumQueryRepositoryImpl;
import com.onebyte.life4cut.common.constants.S3Env;
import com.onebyte.life4cut.common.exception.ErrorCode;
import com.onebyte.life4cut.picture.domain.Picture;
import com.onebyte.life4cut.picture.domain.PictureTag;
import com.onebyte.life4cut.picture.domain.PictureTagRelation;
import com.onebyte.life4cut.picture.repository.PictureRepository;
import com.onebyte.life4cut.picture.repository.PictureTagQueryRepository;
import com.onebyte.life4cut.picture.repository.PictureTagRelationRepository;
import com.onebyte.life4cut.picture.repository.PictureTagRepository;
import com.onebyte.life4cut.support.fileUpload.FileUploadResponse;
import com.onebyte.life4cut.support.fileUpload.FileUploader;
import com.onebyte.life4cut.support.fileUpload.MultipartFileUploadRequest;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PictureService {

    private final SlotQueryRepository slotQueryRepository;

    private final AlbumQueryRepository albumQueryRepository;

    private final UserAlbumQueryRepository userAlbumQueryRepository;

    private final PictureTagQueryRepository pictureTagQueryRepository;

    private final PictureTagRepository pictureTagRepository;

    private final PictureTagRelationRepository pictureTagRelationRepository;

    private final PictureRepository pictureRepository;

    private final FileUploader fileUploader;

    private final S3Env s3Env;

    public PictureService(SlotQueryRepository slotQueryRepository, AlbumQueryRepository albumQueryRepository, UserAlbumQueryRepository userAlbumQueryRepository, PictureTagQueryRepository pictureTagQueryRepository, PictureTagRepository pictureTagRepository, PictureTagRelationRepository pictureTagRelationRepository, PictureRepository pictureRepository, FileUploader fileUploader, S3Env s3Env) {
        this.slotQueryRepository = slotQueryRepository;
        this.albumQueryRepository = albumQueryRepository;
        this.userAlbumQueryRepository = userAlbumQueryRepository;
        this.pictureTagQueryRepository = pictureTagQueryRepository;
        this.pictureTagRepository = pictureTagRepository;
        this.pictureTagRelationRepository = pictureTagRelationRepository;
        this.pictureRepository = pictureRepository;
        this.fileUploader = fileUploader;
        this.s3Env = s3Env;
    }

    @Transactional
    public Long createInSlot(@Nonnull Long authorId, @Nonnull Long albumId, @Nonnull Long slotId, @Nonnull String content, @Nonnull LocalDateTime picturedAt, @Nonnull List<String> tags, @Nonnull MultipartFile image) {
        Album album = albumQueryRepository.findById(albumId).orElseThrow(() -> new AlbumNotFoundException(ErrorCode.ALBUM_NOT_FOUND));
        UserAlbum userAlbum = userAlbumQueryRepository.findByUserIdAndAlbumId(authorId, albumId).orElseThrow(() -> new UserAlbumRolePermissionException(ErrorCode.USER_ALBUM_ROLE_PERMISSION));
        if (userAlbum.isGuest()) {
            throw new UserAlbumRolePermissionException(ErrorCode.USER_ALBUM_ROLE_PERMISSION);
        }

        Slot slot = slotQueryRepository.findById(slotId).orElseThrow(() -> new SlotNotFoundException(ErrorCode.SLOT_NOT_FOUND));
        if (!slot.isIn(album)) {
            throw new AlbumDoesNotHaveSlotException(ErrorCode.ALBUM_DOES_NOT_HAVE_SLOT);
        }

        List<PictureTag> pictureTags = pictureTagQueryRepository.findByNames(tags);
        List<PictureTag> newPictureTags = tags.stream().filter(tag -> pictureTags.stream().noneMatch(pictureTag -> pictureTag.getName().getValue().equals(tag)))
                .map(tag -> PictureTag.create(albumId, authorId, tag)).toList();

        FileUploadResponse response = fileUploader.upload(MultipartFileUploadRequest.of(image, s3Env.bucket()));

        Picture picture = Picture.create(authorId, albumId, response.key(), content, picturedAt);

        pictureRepository.save(picture);
        slot.addPicture(picture.getId());

        pictureTagRepository.saveAll(newPictureTags);

        List<PictureTagRelation> newPictureTagRelations = Stream.concat(pictureTags.stream(), newPictureTags.stream()).map(pictureTag -> PictureTagRelation.create(picture.getId(), albumId, pictureTag.getId())).toList();
        pictureTagRelationRepository.saveAll(newPictureTagRelations);


        return picture.getId();
    }


}
